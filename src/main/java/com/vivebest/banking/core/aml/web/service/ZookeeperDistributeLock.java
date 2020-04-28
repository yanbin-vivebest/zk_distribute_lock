package com.vivebest.banking.core.aml.web.service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;

/**
 * 	利用 ： 去创建指定名称的节点，如果不存在则能创建成功，如果存在则不能创建成功。
 * @author Administrator
 *
 */
public class ZookeeperDistributeLock implements Lock{

	private String zkPath ;
	
	private ZkClient client;
	
	public ZookeeperDistributeLock(String zkPath) {
		super();
		this.zkPath = zkPath;
		client = new ZkClient("localhost:2181");
		client.setZkSerializer(new MyZkSerializer());
	}
	
	@Override
	public void lock() {
		if(!tryLock()) {
			//阻塞自己
			waitForLock();
			
			//再次尝试加锁
			lock();
		}
	}

	private void waitForLock() {
		
		CountDownLatch cdl = new CountDownLatch(1); //只会有一个线程获得锁,所以值为1
		
		IZkDataListener listener = new IZkDataListener() {
			
			@Override
			public void handleDataDeleted(String dataPath) throws Exception {
				System.out.println("监听到及诶到哪被删除");
				cdl.countDown();
			}
			
			@Override
			public void handleDataChange(String dataPath, Object data) throws Exception {
				System.out.println("监听到节点发生改变");
			}
		};
		
		this.client.subscribeDataChanges(this.zkPath, listener);
		if(this.client.exists(zkPath)) {
			try {
				cdl.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		this.client.unsubscribeDataChanges(this.zkPath, listener);
	}

	@Override
	public void unlock() {
		this.client.delete(zkPath);
	}
	
	@Override
	public boolean tryLock() {
		try {
			this.client.createPersistent(zkPath);
		} catch (ZkNodeExistsException e) {
			return false;
		}
		return true;
	}
	
	@Override
	public void lockInterruptibly() throws InterruptedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}

}
