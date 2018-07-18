package codes.demo.singleton;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingletonTest {

	private static ExecutorService executorService = Executors.newFixedThreadPool(
			10,
			new BasicThreadFactory.Builder()
					.namingPattern("email-center-%d").build());

	private static Set list = new ConcurrentSkipListSet();

	private static CountDownLatch latch = new CountDownLatch(10);

	public static void main(String[] args) {
		runWithThreadPool();
	}

	public static void runWithThreadPool() {
		for (int i = 0; i < 10000; i++) {
			executorService.submit(new Runnable() {
				@Override
				public void run() {
					list.add(SingletonByVolatile.getInstance());
					latch.countDown();
				}
			});
		}

		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		executorService.shutdown();
		System.out.println(list.size());
	}

	public static void runWithRunnable() {
		for (int i = 0; i < 10000; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					list.add(SingletonByVolatile.getInstance());
					latch.countDown();
				}
			}, ("Thread" + i)).start();
		}
		System.out.println(latch.getCount());
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(list.size());
	}

	public static void createByEnum() {
		for (int i = 0; i < 10000; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					list.add(SingletonByEnum.INTANCE);
					latch.countDown();
				}
			}, ("Thread" + i)).start();
		}
		System.out.println(latch.getCount());
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(list.size());
	}

}
