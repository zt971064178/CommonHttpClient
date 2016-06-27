package com.wisely.common.httpclient;

import org.apache.http.conn.HttpClientConnectionManager;

/**
 * ClassName: IdleConnectionEvictor  
 * (定时清无效连接)
 * @author zhangtian  
 * @version
 */
public class IdleConnectionEvictor extends Thread {

	private final HttpClientConnectionManager connMgr;

	private volatile boolean shutdown;

	public IdleConnectionEvictor(HttpClientConnectionManager connMgr) {
		this.connMgr = connMgr;
		// 启动当前线程
		this.start();
	}

	@Override
	public void run() {
		System.out.println("清除连接...");
		try {
			while (!shutdown) {
				synchronized (this) {
					wait(5000);
					// 关闭失效的连接
					connMgr.closeExpiredConnections();
				}
			}
		} catch (InterruptedException ex) {
			// 结束
			ex.printStackTrace();
		}
	}

	public void shutdown() {
		shutdown = true;
		synchronized (this) {
			notifyAll();
		}
	}
	
}
