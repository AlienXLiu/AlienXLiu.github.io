package codes.fifth_chapter;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * 模拟
 *
 *
 * @param <T>
 */

public class BlockQueue<T> {

	private Object[] arr;

	// 元素数量
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
		arr = new Object[capacity];
	}

	public boolean add(T t) throws InterruptedException {

		lock.lock();
		try {
			while (count == arr.length) {
				// 如果队列满了就等待
				System.out.println(Thread.currentThread().getName() + " : " + this.toString());
				notFull.await();
			}
			// 增加元素
			arr[count] = t;
			count++;
			// 唤醒notEmpty等待
			notEmpty.signal();
			return true;
		} finally {
			lock.unlock();
		}
	}

	/**
	 *  每次删除都从第一个无素删除
	 * @return
	 * @throws InterruptedException
	 */

	public T remove() throws InterruptedException {

		lock.lock();

		try {
			while (count == 0) {
				System.out.println(Thread.currentThread().getName() + " : " + this.toString());
				notEmpty.await();
			}
			Object o = arr[0];
			Object[] tmp = new Object[arr.length];
			System.arraycopy(arr, 1, tmp, 0, arr.length - 1);
			arr = tmp;
			count--;
			notFull.signal();
			System.out.println("remove count : " + count);
			return (T) o;
		} finally {
			lock.unlock();
		}


	}

	/**
	 *  每次删除都从最后一个元素开始
	 * @return
	 * @throws InterruptedException
	 */

	public T removeFromEnd() throws InterruptedException {

		lock.lock();

		try {
			while (count == 0) {
				System.out.println(Thread.currentThread().getName() + " : " + this.toString());
				notEmpty.await();
			}
			Object o = arr[count -1];
			arr[count -1] = null;
			count--;
			notFull.signal();
			System.out.println("remove count : " + count);
			return (T) o;
		} finally {
			lock.unlock();
		}


	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			sb.append(String.valueOf(arr[i])).append(",");
		}
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		BlockQueue<String> b = new BlockQueue<String>(5);

		b.add("A");    // 173 8614 5879
		b.add("B");
		b.removeFromEnd();
		System.out.println(b.toString());
		b.add("C");
		b.add("D");
		b.add("E");
		System.out.println(b.toString());
		b.removeFromEnd();
		System.out.println(b.toString());
//
//		new Thread(new Runnable() {
//			int index = 0;
//
//			@Override
//			public void run() {
//				while (true) {
//					try {
//						b.add("A" + index++);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}, "Thread-Add").start();
//
//		new Thread(new Runnable() {
//			int index = 0;
//
//			@Override
//			public void run() {
//				while (true) {
//					try {
//						b.removeFromEnd();
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}, "Thread-Remove").start();

	}


}
