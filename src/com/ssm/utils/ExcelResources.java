package com.ssm.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 用于标注在 Bean 的 get 方法上，匹配 Bean 和 Excel。<br/>
 * 可配置属性的标题(title="???")和排序号(order=?,用于导出Excel时标题的显示顺序)。
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelResources {

	String title();

	int order() default 9999;
}
