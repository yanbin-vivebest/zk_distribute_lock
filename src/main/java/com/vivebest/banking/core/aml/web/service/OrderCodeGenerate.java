package com.vivebest.banking.core.aml.web.service;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderCodeGenerate {

	private static int i = 0;
	
	public String getOrderCode() {
		
		Date now = new Date();
		
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS----->");
		
		return sdf.format(now) + ++i;
	}
}
