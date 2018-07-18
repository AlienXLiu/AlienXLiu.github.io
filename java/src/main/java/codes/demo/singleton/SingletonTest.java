package codes.demo.singleton;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.*;

public class SingletonTest {

	private static ExecutorService executorService = Executors.newFixedThreadPool(
			10,
			new BasicThreadFactory.Builder()
					.namingPattern("email-center-%d").build());

	private static Set list = new ConcurrentSkipListSet();

	private static CountDownLatch latch;

	public static void main(String[] args) {
		createByEnum(1000);
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

	public static void createByEnum(int loop) {
		latch = new CountDownLatch(loop);
		for (int i = 0; i < loop; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					list.add(SingletonByEnum.INTANCE.getInstance());
					try {
						TimeUnit.MILLISECONDS.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
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

		System.out.println("=====List Size========" + list.size());
		System.out.println("=====List Elements========");
		for (Object it : list) {
			System.out.println(it.toString());
		}
	}

}
