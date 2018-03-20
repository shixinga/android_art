package com.csx;

public class Looper {

	final MessageQueue mMessageQueue = new MessageQueue();
	private static ThreadLocal<Looper> sThreadLocal = new ThreadLocal<>();
	public static void prepareLoop() {
		if (sThreadLocal.get() != null) {
			throw new RuntimeException("Only one Looper may be created per thread");
		}
		sThreadLocal.set(new Looper());
	}
	
	public static Looper myLooper() {
		return sThreadLocal.get();
	}
	public static void loop() {
		Looper looper = myLooper();
		if(looper == null) {
			throw new RuntimeException("No Looper; Looper.prepare() wasn't called on this thread.");
		}
		MessageQueue m = looper.mMessageQueue;
		for(;;) {
			Message msg = m.next();
			msg.target.dispathMessage(msg);
		}
	}
}
