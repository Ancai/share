package com.share.test;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.apache.commons.lang.time.DateUtils;


import com.share.util.SpringUtil;
/** 
 * 测试类：Java特性
 * 
 * @author Ancai email: ancai0729@gmail.com
 * @since 2012-8-4 下午8:35:41
 * @version 1.0
 */
public class JavaTest {
	public static void main(String[] args) {
		//System.out.println("Date:"+new Date());
		//System.out.println(new Timestamp(100000009));
		
		//Apache Common 携带的时间工具类测试
		//System.out.println(DateUtils.addDays(new Date(), -15));
		Date now = new Date();
		Iterator iterator = DateUtils.iterator(now, DateUtils.RANGE_MONTH_MONDAY);
		TimeUnit.valueOf("");
		DataSource ds = SpringUtil.getBean("dataSource");
		while (iterator.hasNext()) {
			System.out.println(((java.util.GregorianCalendar)iterator.next()).get(GregorianCalendar.DAY_OF_MONTH));
		}
		System.out.println(DateUtils.addDays(new Date(), -15));
		Long[] numbers = new Long[]{};
	}
}
