package codes.fourth_chapter.threadpool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job> {

	private static final int MAX_WORKER_NUMBERS = 10;

	private static final int MIN_WORKER_NUMBERS = 2;

	private static final int DEFAULT_WORKER_NUMBERS = 5;

	// 工作列表
	private final LinkedList<Job> jobs = new LinkedList<>();

	// 工作者列表
	private final List<Worker> workers = Collections.synchronizedList(new ArrayList<>());

	// 工作者线程数量
	private int workerNumber = DEFAULT_WORKER_NUMBERS;

	// 线程编号
	private AtomicLong threadNumber = new AtomicLong(0);

	public DefaultThreadPool() {
		initializeWokers(DEFAULT_WORKER_NUMBERS);
	}

	public DefaultThreadPool(int init) {
		workerNumber = init > MAX_WORKER_NUMBERS ? MAX_WORKER_NUMBERS : init < MIN_WORKER_NUMBERS ? MIN_WORKER_NUMBERS : init;
		initializeWokers(workerNumber);
	}

	private void initializeWokers(int capacity) {
		for (int i = 0; i < capacity; i++) {
			Worker worker = new Worker();
			workers.add(worker);
			Thread thread = new Thread(worker, "ThreadPool-Worker-" + threadNumber.incrementAndGet());
			thread.start();
		}
	}


	@Override
	public void execute(Job job) {
		if (null != job) {
			synchronized (jobs) {
				jobs.addLast(job);
				jobs.notify();
			}
		}
	}

	@Override
	public void shutdown() {
		for (Worker worker : workers) {
			worker.shutdown();
		}
	}

	@Override
	public void addWorkers(int num) {
		if (num + this.workerNumber > MAX_WORKER_NUMBERS) {
			num = MAX_WORKER_NUMBERS - this.workerNumber;
		}

		initializeWokers(num);
		this.workerNumber += num;

	}

	@Override
	public void removeWorker(int num) {
		synchronized (jobs) {
			if (num >= workerNumber) {
				throw new IllegalArgumentException("beyond workNumber!");
			}
			int count = 0;
			while (count < num) {
				Worker worker = workers.get(count);
				if (workers.remove(worker)) {
					count++;
					worker.shutdown();
				}
			}
			this.workerNumber -= count;
		}
	}

	@Override
	public int getJobSize() {
		return this.jobs.size();
	}


	class Worker implements Runnable {
		private volatile boolean running = true;

		@Override
		public void run() {
			while (running) {
				Job job;
				synchronized (jobs) {
					while (jobs.isEmpty()) {
						try {
							jobs.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
							Thread.currentThread().interrupt();
							return;
						}
					}

					job = jobs.removeFirst();
				}

				if (job != null) {
					job.run();
				}
			}
		}

		public void shutdown() {
			running = false;
		}
	}
}
