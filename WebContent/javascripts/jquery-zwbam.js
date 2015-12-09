/**
 * @author Ezios
 * @extends jquery-1.8.3
 * @version 0.2.9
 * @date 2015-3-4
 * @describe
 *     后台管理模块通用库
 *     1.侧边菜单生成器
 *     2.顶部菜单生成器
 *
 * @example
 *     1.菜单生成器
 *     $('.left-menu-list').zwbam('initMenu', data);
 *     data为json对象数组或后台API接口
 *     {
            "d": [{
                "name": "基本设置",
                "url": "#",
                "childs": [{
                    "name": "栏目管理",
                    "url": "#",
                    "childs": [{
                        "name": "用户组管理",
                        "url": "/usergroup/tolist.do"
                    }, {
                        "name": "用户管理",
                        "url": "/user/tolist.do"
                    }]
                }, {
                    "name": "用户组管理",
                    "url": "#",
                    "childs": [{
                        "name": "用户组管理",
                        "url": "/usergroup/tolist.do"
                    }]
                }]
            }]
        }
 */
(function($) {
    //方法集
    var _methods = {
        //初始化
        init: function() {
            console.log('call init()');
        },
        //设置顶部菜单
        setTopMenu: function(itemName){
            if(typeof itemName === 'string'){
                var $aim = $('.icon-ul').find('li[data-title=' + itemName + ']');

                if($aim.length){
                    $('.icon-ul').find('li a.selected').removeClass('selected');
                    $aim.find('a').addClass('selected');
                }
            }
        },
        //得到当前选中的Menu菜单
        getCurrentSelectedTopMenuUrl:function(){
        	 return $('.icon-ul').find('li a.selected').attr("href");
        },
        //菜单生成器
        initMenu: function(option) {
            //目标dom
            var $doms = $(this);
            //生成器
            var maker = function(item){
                //一级dom
                var $firstNodeDom = $('<div class="left-menu-first-node"><div class="first-title"><div class="text">' + item.name + '</div></div><div class="first-children"></div></div>');
                //二级菜单dom
                var $secondNodeDom = $('<div class="left-menu-second-node"><div class="second-title"><i class="icon"></i></div></div>');
                //三级菜单dom
                var $thirdNodeDom = $('<ul class="menu-son"></ul>');

                if(! $.isEmptyObject(item.childs)){
                    var secondNode = item.childs;

                    for (var i = 0; i < secondNode.length; i++) {
                        var $secondNodeDomClone = $secondNodeDom.clone();

                        //链接地址
                        if(secondNode[i].url !== '#'){
                            $secondNodeDomClone.find('.second-title').append('<a href="' + secondNode[i].url + '" class="text navigation" data-id="' + secondNode[i].id + '" target="rightFrame">' + secondNode[i].name + '</a>');
                        }else{
                            $secondNodeDomClone.find('.second-title').append('<span class="text">' + secondNode[i].name + '</span>');
                        }

                        //三级菜单
                        if(! $.isEmptyObject(secondNode[i].childs)){
                            var thirdNode = secondNode[i].childs;
                            var $thirdNodeDomClone = $thirdNodeDom.clone();

                            if(secondNode[i].open === true){
                                $thirdNodeDomClone.show();
                            }

                            for (var j = 0; j < thirdNode.length; j++) {
                                var $liclone = $('<li><i class="li-icon"></i><a href="' + thirdNode[j].url + '" class="navigation" data-id="' + thirdNode[j].id + '" target="rightFrame">' + thirdNode[j].name + '</a></li>');

                                $thirdNodeDomClone.append($liclone);
                            };

                            $secondNodeDomClone.append($thirdNodeDomClone);
                        }

                        $firstNodeDom.find('.first-children').append($secondNodeDomClone);
                    };
                }

                if(item.open === true){
                    $firstNodeDom.find('.first-children').show();
                }
                $doms.append($firstNodeDom);
            };
            //注册器
            var register = function($aim, json){
                var scroller = '';

                //快捷菜单点击
                $('.left-menu-top .navigation').bind('click', function(event) {
                    $('.left-menu .active').removeClass('active');
                    $(this).addClass('active');
                });
                //第一级菜单点击
                $aim.find('.first-title').bind('click', function(event){
                    var $children = $(this).nextAll('.first-children');

                    $aim.find('.first-children:visible').slideUp();
                    if ($children.is(':visible')){
                        $children.slideUp();
                    }else{
                        $children.slideDown();
                    }
                });
                //第二级菜单点击
                $aim.find('.second-title').bind('click', function(event) {
                    var $children = $(this).parent().find('.menu-son');

                    $aim.find('.menu-son:visible').slideUp();
                    $('.left-menu .active').removeClass('active');
                    if ($children.is(':visible')) {
                        $children.slideUp();
                    }else{
                        $children.slideDown();
                        $(this).addClass('active');
                    }
                }).hover(function() {
                    $(this).addClass('hover');
                }, function() {
                    $aim.find('.second-title').removeClass('hover');
                });
                //第三级菜单点击
                $aim.find('.menu-son li').bind('click', function(event) {
                    $('.left-menu .active').removeClass('active');
                    $(this).addClass('active');
                }).hover(function() {
                    $(this).addClass('hover');
                }, function() {
                    $aim.find('.menu-son li').removeClass('hover');
                });
            };
            var init = function(json){
                if(json.d && ! $.isEmptyObject(json.d)){
                    json = $.extend({
                        enablePlace: true
                    }, json);

                    $doms.html('');

                    for (var i = 0; i < json.d.length; i++) {
                        maker(json.d[i]);
                    };

                    register($doms, json);

                    if(typeof option.onCompleted === 'function'){
                        option.onCompleted();
                    }
                }else{
                    alert('菜单数据为空');
                }
            };
            //API接口
            if(option && typeof option.data === 'string'){
                $.getJSON(option.data, function(json, textStatus) {
                    if (! $.isEmptyObject(json)) {
                        init(json);
                    }else{
                        alert('获取菜单数据失败');
                    }
                });
            //数据对象
            }else if(option && typeof option.data === 'object'){
                if (! $.isEmptyObject(option.data)) {
                    init(option.data);
                }else{
                    alert('请检查菜单配置项');
                }
            }
        },
        /*顶部菜单生成器*/
        initTopMenu: function(option){
            var $me = this;
            var data = {};
            var pageData = [];
            var iconList = $me.find('.icon-list');
            var maker = function(data){
                var list = $me.find('.icon-ul');

                list.html('');

                for (var i = 0; i < data.length; i++) {
                    list.append('<li data-title="' + data[i].title + '"><a class="pngfix" href="' + data[i].url + '" target="contentFrame"><img src="' + data[i].imgUrl + '" alt="' + data[i].title + '" /><p title="' + data[i].title + '">' + data[i].title + '</p></a></li>');
                    $('#mini-list').append('<a href="' + data[i].url + '" target="contentFrame">' + data[i].title + '</a>');
                };

                $me.find('.icon-list').data({
                    page: 1
                });
            };
            var changeList = function(){
                //可用区域宽度 顶部最上级div .header内部宽度 减去 logo区域宽度 用户区域宽度 网站标题区域宽度
                var width = $('.header').width() - $('.logo').outerWidth() - $('.function-block').outerWidth() - $('.web-title').outerWidth();
                //每页显示的个数
                var nums = parseInt(width / 78) - 1;
                //页数
                var pageNums = 0;
                var returnRemainder = function(dividend, divisor){
                    return (dividend / divisor) % parseInt(dividend / divisor) === 0 ? parseInt(dividend / divisor) : parseInt(dividend / divisor) + 1;
                };

                //个数小于0强制为1
                nums = nums === 0 ? 1 : nums;
                //个数超过8个强制为8
                nums = nums > 8 ? 8 : nums;
                pageNums = returnRemainder(data.length, nums);

                iconList.width(nums * 78);

                iconList.data({
                    pageNums: pageNums,
                    width: 78
                });
                //初始化第一页
                loadPage(1);

                //当数据条数超过一页限制时显示下一页按钮
                if(data.length > nums){
                    $me.find('.next-list').show();
                    if(pageNums <= 4){
                        $me.find('.dots-list').children().remove();
                        for(var i = 0; i < pageNums; i++){
                            $('.dots-list').append('<li data-nums="' + (i + 1) + '"><a href="javascript:void(0);" title="第' + (i + 1) + '页"></a></li>');
                        }
                        changeDot();
                    }else{
                        $me.find('.dots-list').hide();
                        $me.find('.total-page').text(pageNums);
                        $me.find('.nums-list').show();
                    }
                }else{
                    $me.find('.next-list').hide();
                }
            };
            var loadPage = function(nums){
                nums = parseInt(nums);

                if(nums > iconList.data('pageNums')){
                    nums = 1;
                }

                $me.find('.icon-ul').animate({
                    marginLeft: '-' + iconList.width()*(nums - 1) + 'px'
                },'fast');

                iconList.data({
                    page: nums
                });

                if(iconList.data('pageNums') <= 4){
                    changeDot();
                }else{
                    $me.find('.cur-page').text(nums);
                }
            };
            var changeDot = function(){
                $me.find('.dots-list li.selected').removeClass('selected');
                $me.find('.dots-list li').eq(iconList.data('page') - 1).addClass('selected');
            };
            var register = function(){
                //注册窗口大小变化事件
                // $(window).resize(function(){
                //     changeList();
                // });
                $me.find('#next-btn').click(function() {
                    loadPage(iconList.data('page') + 1);
                });
                $me.find('.icon-ul li a').click(function(){
                    $me.find('.icon-ul li a.selected').removeClass('selected');
                    $(this).addClass('selected');
                });
                $me.find('.dots-list li').click(function(){
                    loadPage($(this).attr('data-nums'));
                });
            };
            var init = function(){
                //生成列表
                maker(data);
                //修复IE6 PNG
                if(typeof fixpng !== 'undefined'){
                    fixpng();
                }
                //初始化列表显示
                changeList();
                //注册事件
                register();

                if(typeof option.callback === 'function'){
                    option.callback();
                }
            };

            //API接口
            if(typeof option === 'string'){
                $.getJSON(option, function(json, textStatus) {
                    if (! $.isEmptyObject(json)) {
                        data = json.d;
                        init();
                    }else{
                        alert('获取顶部菜单数据失败');
                    }
                });
            //数据对象
            }else if(typeof option === 'object'){
                if (! $.isEmptyObject(option)) {
                    data = option.d;
                    init();
                }else{
                    alert('请检查菜单配置项');
                }
            }
        }
    };

    $.fn.zwbam = function(method) {
        if (_methods[method]) { //调用_methods中的方法
            return _methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof method === 'object' || !method) { //当参数是对象时 初始化
            return _methods.init.apply(this, arguments);
        } else { //未找到参数指明的方法
            $.error('错误' + method + ' 该方法并未在zwbam中定义');
        }
    };
})(jQuery);