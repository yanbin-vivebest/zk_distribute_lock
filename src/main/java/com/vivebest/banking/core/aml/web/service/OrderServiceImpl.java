package com.vivebest.banking.core.aml.web.service;


/**
 * 	没有解决高并发问题	
 * @author Administrator
 *
 */
public class OrderServiceImpl implements OrderService {

	private static OrderCodeGenerate ocg = new OrderCodeGenerate();
	
	@Override
	public void createOrder() {
		
		String orderCode = null;
		
		orderCode = ocg.getOrderCode();
		
		System.out.println(Thread.currentThread().getName() + "=========" + orderCode);
		
	}
}
