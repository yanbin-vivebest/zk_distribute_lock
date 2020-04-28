package com.vivebest.banking.core.aml.web.service;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 	目的就是在于模拟高并发
 * 	第一种方式用CyclicBarrier，等到10个线程都执行到那一句代码后，再一起执行后面的操作。
 *	如何解决这种高并发问题看createOrder
 */
public class Client {

	public static void main(String[] args) {
		
		//模拟多线程
		int currs = 10;
		
		CyclicBarrier cb = new CyclicBarrier(currs);
		
		for(int i = 0;i<currs;i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					OrderService os = new OrderServiceImpl3();
					
					try {
						cb.await();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (BrokenBarrierException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					os.createOrder();
				}
			}).start();
		}
	}
}
