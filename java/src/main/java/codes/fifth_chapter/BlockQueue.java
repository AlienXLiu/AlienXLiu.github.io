package codes.fifth_chapter;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockQueue<T> {

	private Object[] arr;

	// 增加下标
	private int addIndex;

	// 删除下标
	private int removeIndex;

	// 队列数量
	private int count;

	private Lock lock = new ReentrantLock();
	// 队列没空
	private Condition notEmpty = lock.newCondition();
	// 队列没满
	private Condition notFull = lock.newCondition();


	public BlockQueue(int capacity) {
		if (capacity <= 0) {
			throw new IllegalArgumentException("capacity must great than zero !");
		}
		this.count = capacity;
	}

	public boolean add(T t) throws InterruptedException {

		lock.lock();
		try {
			while (count == arr.length) {
				// 如果队列满了就等待
				notFull.await();
			}
			// 增加元素
			arr[addIndex] = t;
			addIndex++;
			// 唤醒notEmpty等待
			notEmpty.signal();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		return false;
	}

	public void remove() {

	}

	public static void main(String[] args) {
		int a = 0;
		int x = test(a);

		System.out.println("main print X : " + x);
		System.out.println("main print a : " + a);
	}

	public static int test(int x) {
		try {
			x = 2;
		} catch (Exception e) {
			e.printStackTrace();
			x = 1;
			System.out.println("catch print : " + x);
			return x;
		} finally {
			x = 2;
			System.out.println("finally print : " + x);
//			return a;
		}
		x = 3;
		System.out.println("test over print : " + x);
		return x;
	}

}
