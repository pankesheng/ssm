$(function(){
    //标签页
    $('[data-toggle=tab]').each(function(){
        var $tabsBtn = $(this).find('.tabs-btn');

        $tabsBtn.bind('click', $tabsBtn, changeTab);
    });
    //标签
    $(document).on('click', '.tags .close', function(){
        $(this).parents('.tags').remove();
    });
});

/**
 * 显示提示
 * @param  {String} tipString 提示字符串
 * @param  {[type]} type      success 绿色 danger 红色 info 蓝色 warning 黄色
 */
function tip(tipString, type) {
    var $dom = $(
        '<div class="dialog alert-tip alert alert-' + type + '">' +
            '<div>' + tipString + '</div>' +
        '</div > '
    );
    var offset = '';

    $('body').append($dom);
    offset = _getCenterPoint($dom);

    $dom.show().css({
        top: 15,
        left: offset.left
    });

    window.setTimeout(function() {
        $dom.fadeOut('fast', function() {
            $dom.remove();
        });
    }, 2000);
}

/**
 * 显示iframe
 * @param  {String} title  标题
 * @param  {String} url    url地址
 * @param  {Number} width  宽度
 * @param  {Number} height 高度
 * @return {Number} index  层级
 */
function showIframe(title, url, width, height){
    if(typeof $.layer !== 'undefined'){
        if(!title){
            title = false;
        }
        return $.layer({
            type: 2,
            title: title,
            maxmin: true,
            shadeClose: true,
            area : [width + 'px' , height + 'px'],
            iframe: {src: url}
        });
    }
}

/**
 * 显示dom
 * @param  {String} title  标题
 * @param  {Object} dom    dom
 * @param  {Number} width  宽度
 * @param  {Number} height 高度
 * @return {Number} index  层级
 */
function showDom (title, dom, width, height) {
    if(typeof $.layer !== 'undefined'){
        if(!title){
            title = false;
        }
        $(dom).find('.form-wrap').width(width - 42).height(height - 77);
        return $.layer({
            type: 1,
            title: title,
            maxmin: false,
            shadeClose: true,
            area : [width + 'px' , height + 'px'],
            page: {dom: dom}
        });
    }
}

/**
 * 显示alert
 * @param  {String} msg    字符串
 * @param  {Number} width  宽度
 * @param  {Number} height 高度
 * @return {Number} index  层级
 */
function showAlert(msg, yes, no) {
    if(typeof $.layer !== 'undefined'){
        return $.layer({
            shade: [0.5, '#000'],
            area: ['auto','auto'],
            dialog: {
                msg: msg,
                type: -1,
                btns: 2,                    
                btn: ['确定','取消'],
                yes: yes
            }
        });
    }
}

function changeTab (event) {
    event.preventDefault();

    event.data.removeClass('selected');
    $(this).addClass('selected');

    $($(this).attr('href')).siblings().hide()
        .end().show();
}

/**
 * 标签
 * @param  {[type]} config [description]
 * @return {[type]}        [description]
 */
function tags(config){

}

/**
 * 显示遮罩
 */
function showShade(){
    if($('body .shade').length){
        return false;
    }else{
        $('body').append('<div class="shade"></div>')
    }
}
/**
 * 隐藏遮罩
 */
function hideShade(){
    $('body .shade').remove();
}

function _getCenterPoint($dom) {
    return {
        left: $(document).width() / 2 - $dom.outerWidth() / 2,
        top: $(document).height() / 2 - $dom.outerHeight() / 2
    };
}