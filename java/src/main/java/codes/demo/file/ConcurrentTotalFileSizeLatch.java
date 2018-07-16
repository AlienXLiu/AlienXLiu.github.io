package codes.demo.file;

import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by ZhenxingLiu on 2017/9/15.
 */
public class ConcurrentTotalFileSizeLatch {
	final CountDownLatch latch = new CountDownLatch(1);
	//文件大小
	final AtomicLong fileSize = new AtomicLong();
	//访问的文件数量
	final AtomicLong files = new AtomicLong();

	final ExecutorService service = Executors.newFixedThreadPool(10);

	public long getTotalFileSize(File file) {
		files.incrementAndGet();
		try {
			updateFileSize(file);
			latch.await(10, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			service.shutdown();
		}

		return fileSize.longValue();
	}

	private void updateFileSize(File file) {
		long size = 0;
		if (file.isFile()) {
			size += file.length();
		} else {
			File[] fileList = file.listFiles();
			for (File f : fileList) {
				files.incrementAndGet();
				service.submit(new Runnable() {
					@Override
					public void run() {
						updateFileSize(f);
					}
				});
			}
		}
		fileSize.addAndGet(size);
		if (files.decrementAndGet() == 0) {
			latch.countDown();
		}

	}

	public static void main(String[] args) {
		final long start = System.currentTimeMillis();
		final long total = new ConcurrentTotalFileSizeLatch().getTotalFileSize(new File("D:\\Document"));
		final long end = System.currentTimeMillis();
		System.out.println("Total Size : " + total);
		System.out.println("Cost time : " + (end - start));
	}
}
