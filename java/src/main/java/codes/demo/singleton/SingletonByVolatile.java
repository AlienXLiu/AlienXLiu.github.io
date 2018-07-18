package codes.demo.singleton;

import java.util.concurrent.TimeUnit;

/**
 * 此类单列线程安全
 * 但是反射可以破坏其单列模式
 */

public class SingletonByVolatile implements Comparable {

	private SingletonByVolatile() {
		// 模拟类初始化时耗时
		try {
			TimeUnit.MILLISECONDS.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private static volatile SingletonByVolatile INSTANCE;

	public static SingletonByVolatile getInstance() {
		if (INSTANCE == null) {
			synchronized (SingletonByVolatile.class) {
				if (INSTANCE == null) {
					INSTANCE = new SingletonByVolatile();
				}
			}
		}
		return INSTANCE;
	}

	@Override
	public int compareTo(Object o) {
		if (this == o) {
			return 0;
		}
		if (o == null) {
			return -1;
		}
		if (this.hashCode() > o.hashCode()) {
			return 1;
		} else {
			return -1;
		}
	}
}
