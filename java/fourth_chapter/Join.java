package fourth_chapter;

import java.util.concurrent.TimeUnit;

public class Join {

	public static void main(String[] args) throws Exception {
		Thread pre = Thread.currentThread();
		for (int i = 0; i < 10; i++) {
			Thread thread = new Thread(new Domino(pre), ("thread " + i));
			thread.start();
			pre = thread;
		}
		TimeUnit.SECONDS.sleep(2);
		System.out.println("Main : " + Thread.currentThread().getName());
	}


	static class Domino implements Runnable {

		private Thread thread;

		public Domino(Thread thread) {
			this.thread = thread;
		}

		@Override
		public void run() {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Domino : " + Thread.currentThread().getName());
		}
	}
}
