package com.vivebest.banking.core.aml.web.service;


/**
 * 	解决高并发问题	
 * 	第一种 : 加锁，只用static类型的变量加锁
 * @author Administrator
 *
 */
public class OrderServiceImpl2 implements OrderService {

	private static OrderCodeGenerate ocg = new OrderCodeGenerate();
	
	@Override
	public void createOrder() {
		
		String orderCode = null;
		
		synchronized(ocg) {
			orderCode = ocg.getOrderCode();
			System.out.println(Thread.currentThread().getName() + "=========" + orderCode);
		}
		
		//下面这种方式没有用,因为外面是通过new的方式,每个对象都会有自己的锁，那就相当于没有锁。
//		synchronized(this) {
//			orderCode = ocg.getOrderCode();
//			System.out.println(Thread.currentThread().getName() + "=========" + orderCode);
//		}
	}
}
