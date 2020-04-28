package com.vivebest.banking.core.aml.web.service;

import java.util.concurrent.CountDownLatch;

/**
 * 	目的就是在于模拟高并发
 * 	第二种方式就是用 CountDownLatch
 * 	如何解决这种高并发问题看createOrder
 */
public class Client2 {

	public static void main(String[] args) {
		
		//模拟多线程
		int currs = 10;
		
		CountDownLatch cdl = new CountDownLatch(currs);
		
		for(int i = 0;i<currs;i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					OrderService os = new OrderServiceImpl();
					
					
					cdl.countDown(); //相当于减1，等10个全部减完，再一起执行 os.createOrder();
					try {
						cdl.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					os.createOrder();
				}
			}).start();
		}
	}
}
