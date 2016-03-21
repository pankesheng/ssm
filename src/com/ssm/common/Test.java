package com.ssm.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test {
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
		calendar.add(Calendar.DATE,1 - (calendar.get(Calendar.DAY_OF_WEEK)) );
		Date zhouriDate = calendar.getTime();
		
		calendar.setTime(date);
		calendar.add(Calendar.DATE,1 - (calendar.get(Calendar.DAY_OF_WEEK)-1));
		Date zhouyiDate = calendar.getTime();
		
		calendar.setTime(date);
		calendar.add(Calendar.DATE,1 - (calendar.get(Calendar.DAY_OF_WEEK)-2));
		Date zhouerDate = calendar.getTime();
		
		calendar.setTime(date);
		calendar.add(Calendar.DATE,1 - (calendar.get(Calendar.DAY_OF_WEEK)-3));
		Date zhousanDate = calendar.getTime();
		
		calendar.setTime(date);
		calendar.add(Calendar.DATE,1 - (calendar.get(Calendar.DAY_OF_WEEK)-4));
		Date zhousiDate = calendar.getTime();
		
		calendar.setTime(date);
		calendar.add(Calendar.DATE,1 - (calendar.get(Calendar.DAY_OF_WEEK)-5));
		Date zhouwuDate = calendar.getTime();
		
		calendar.setTime(date);
		calendar.add(Calendar.DATE,1 - (calendar.get(Calendar.DAY_OF_WEEK)-6));
		Date zhouliuDate = calendar.getTime();
		
		calendar.setTime(date);
		calendar.add(Calendar.DATE,1 - (calendar.get(Calendar.DAY_OF_WEEK)-7));
		Date zhouriDate2 = calendar.getTime();
		
		System.out.println("周日："+sdf.format(zhouriDate));
		System.out.println("周一："+sdf.format(zhouyiDate));
		System.out.println("周二："+sdf.format(zhouerDate));
		System.out.println("周三："+sdf.format(zhousanDate));
		System.out.println("周四："+sdf.format(zhousiDate));
		System.out.println("周五："+sdf.format(zhouwuDate));
		System.out.println("周六："+sdf.format(zhouliuDate));
		System.out.println("周日："+sdf.format(zhouriDate2));
		System.out.println("end................");
	}

}
