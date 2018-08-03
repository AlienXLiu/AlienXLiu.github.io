package codes.sixth_chapter;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class HashMapTest {
	/**
	 * HashMap 在并发执行put操作时会引起死循环
	 * 原因：多线程会导致HashMap的entry链表形成环形数据节结，一旦形成环形数据节构,
	 * entry的next节点永远不为空，就会产生死循环获取entry。
	 *
	 * JDK1.8 已经没有此BUG了......但是多线程put会有数据丢失哦
	 *
	 */

	public static void main(String[] args) throws InterruptedException {
		final HashMap<String, String> map = new HashMap<String, String>(2);
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 100000; i++) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								TimeUnit.MILLISECONDS.sleep(new Random(100).nextLong());
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							map.put(UUID.randomUUID().toString(), "");
						}
					}, "innerThread" + i).start();
				}
			}
		}, "outThread");
		thread.start();
		thread.join();
		System.out.println(map.size());
	}

}
