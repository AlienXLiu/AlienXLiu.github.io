package codes.demo.file;

import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by ZhenxingLiu on 2017/9/15.
 */
public class ContFileQueue {
	final ExecutorService service = Executors.newFixedThreadPool(10);
	final BlockingQueue<Long> queue = new ArrayBlockingQueue<Long>(500);
	final AtomicLong fileVists = new AtomicLong();

	public long getTotalFileSize(final File directory) {
		startExploreDir(directory);
		long totalSize = 0;
		try {

			while (fileVists.longValue() > 0 || !queue.isEmpty()) {
				final long size = queue.poll(10, TimeUnit.SECONDS);
				totalSize += size;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		service.shutdown();
		return totalSize;
	}

	private void startExploreDir(final File file) {
		fileVists.incrementAndGet();
		service.submit(new Runnable() {
			@Override
			public void run() {
				exporeDir(file);
			}
		});
	}

	private void exporeDir(final File file) {
		long fileSize = 0;
		if (file.isFile()) {
			fileSize = file.length();
		} else {
			final File[] fileList = file.listFiles();
			if (null != fileList) {
				for (File f : fileList) {
					if (f.isFile()) {
						fileSize += f.length();
					} else {
						startExploreDir(f);
					}
				}
			}
		}

		try {
			queue.put(fileSize);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		fileVists.decrementAndGet();
	}

	public static void main(String[] args) {
		final long start = System.currentTimeMillis();
		final long total = new ContFileQueue().getTotalFileSize(new File("D:\\Document"));
		final long end = System.currentTimeMillis();
		System.out.println("Total Size : " + total);
		System.out.println("Cost time : " + (end - start));
	}
}
