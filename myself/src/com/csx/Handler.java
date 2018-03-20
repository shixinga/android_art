package com.csx;

public class Handler {

	MessageQueue mMessageQueue;
	Looper mLooper;
	
	public Handler() {
		this.mLooper = Looper.myLooper();
		if(mLooper == null) {
			throw new RuntimeException("No Looper; Looper.prepare() wasn't called on this thread.");
		}
		mMessageQueue = this.mLooper.mMessageQueue;
	}

	public void handleMassage(Message msg) {
		
	}
	
	public void dispathMessage(Message msg) {
		handleMassage(msg);
	}
	
	public void sendMessage(Message msg) {
		msg.target = this;
		mMessageQueue.enqueMessage(msg);
	}
}
