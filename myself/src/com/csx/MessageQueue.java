package com.csx;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MessageQueue {

	Message [] messages = new Message[50];
	private Lock lock;
	private Condition conditionNotEmpty;
	private Condition conditionNotFull;
	int count;
	int headIndex;
	int tailIndex;
	public MessageQueue() {
		this.lock = new ReentrantLock();
		this.conditionNotEmpty = this.lock.newCondition();
		this.conditionNotFull = this.lock.newCondition();
	}

	public void enqueMessage(Message msg) {
		try {
			this.lock.lock();
			while(count == messages.length) {
				try {
					conditionNotFull.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			messages[tailIndex++] = msg;
			count++;
			if(tailIndex == messages.length) {
				tailIndex = 0;
			}
			this.conditionNotEmpty.signalAll();
		} finally {
			this.lock.unlock();
		}
	}
	
	public Message next() {
		try {
			this.lock.lock();
			while(count == 0) {
				try {
					this.conditionNotEmpty.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			Message msg = messages[headIndex++];
			count--;
			if(headIndex == messages.length) {
				headIndex = 0;
			}
			this.conditionNotFull.signalAll();
			return msg;
		} finally {
			this.lock.unlock();
		}
	}
}
