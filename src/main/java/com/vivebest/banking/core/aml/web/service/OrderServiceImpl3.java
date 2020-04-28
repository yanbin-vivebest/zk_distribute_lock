package com.vivebest.banking.core.aml.web.service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 	解决高并发问题	
 * 	第二种 : 使用lock
 * @author Administrator
 *
 */
public class OrderServiceImpl3 implements OrderService {

	private static OrderCodeGenerate ocg = new OrderCodeGenerate();
	
	private static Lock lock = new ReentrantLock();
	
	@Override
	public void createOrder() {
		
		String orderCode = null;
		
		lock.lock();
		
		try {
			orderCode = ocg.getOrderCode();
			System.out.println(Thread.currentThread().getName() + "=========" + orderCode);
		} catch (Exception e) {
		}finally {
			lock.unlock();
		}
		
		//下面这种方式没有用,因为外面是通过new的方式,每个对象都会有自己的锁，那就相当于没有锁。
//		synchronized(this) {
//			orderCode = ocg.getOrderCode();
//			System.out.println(Thread.currentThread().getName() + "=========" + orderCode);
//		}
	}
}
