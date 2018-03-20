package com.csx.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.csx.Handler;
import com.csx.Looper;
import com.csx.Message;

public class Main {

	public static void main(String[] args) {
		ExecutorService service = Executors.newFixedThreadPool(10);
		Looper.prepareLoop();
		final Handler handler = new Handler() {
			@Override
			public void handleMassage(Message msg) {
				System.out.println(Thread.currentThread().getName() + " handle:" + msg.obj);
			}
		};
		for (int i = 0; i < 10; i++) {
			service.execute(new Runnable() {
				
				@Override
				public void run() {
					while(true) {
						Message msg=  new Message();
						msg.what = 1;
						msg.obj = Thread.currentThread().getName() + ",send message";
						handler.sendMessage(msg);
						try {
							TimeUnit.SECONDS.sleep(2);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			});
		}
		
		Looper.loop();
	}
}
