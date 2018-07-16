package codes.fifth_chapter;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class TwinsLockTest {

	static final Lock lock = new TwinsLock();

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			Worker worker = new Worker(i + " 线程");
			worker.setDaemon(true);
			worker.start();
		}
		for (int i = 0; i < 10; i++) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (Exception e) {

			}
			System.out.println();
		}
	}

	static class Worker extends Thread {

		Worker(String name) {
			setName(name);
		}

		@Override
		public void run() {
			while (true) {
				lock.lock();
				try {
					TimeUnit.SECONDS.sleep(1);
					System.out.println(Thread.currentThread().getName());
					TimeUnit.SECONDS.sleep(1);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
			}
		}
	}
}
