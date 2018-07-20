package codes.fifth_chapter;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class ParkTest {

	public static void main(String[] args) {

		/**
		 *
		 * sleep， 进入TIMED_WAITING状态，不出让锁
		 *
		 * wait, 进入TIMED_WAITING状态，出让锁，并进入对象的等待队列
		 *
		 * park, 进入WAITING状态，对比wait不需要获得锁就可以让线程WAITING，通过unpark唤醒
		 * 		 被中断不会抛出 java.lang.InterruptedException
		 *
		 * interrupt, 只是给线程发个信号，如果在wait, sleep会收到exception
		 *
		 * yeild, 在操作系统层面让线程从running变成ready状态，等待继续被调度。在jvm的线程状态还是RUNNABLE
		 *
		 *
		 */

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(100000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

//				parkWithNoBlocker();
			}
		}, "parkWithNoBlocker");

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(100000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
//				parkWithBlocker();
			}
		}, "parkWithBlocker");


		t1.start();
		t2.start();

		System.out.println("-----主线程休眠------------");
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("-----中断t1、t2------------");

		t1.interrupt();
		t2.interrupt();

		System.out.println("-----main over------------");
	}

	public static void parkWithNoBlocker() {
		LockSupport.park();
		System.out.println("parkWithNoBlocker");
	}

	public static void parkWithBlocker() {
		LockSupport.park(ParkTest.class);
		System.out.println("parkWithBlocker");
	}
}
