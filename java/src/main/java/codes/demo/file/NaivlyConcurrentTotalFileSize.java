package codes.demo.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by ZhenxingLiu on 2017/9/15.
 * <p>
 * 由于程序是递归且线程池大小固定，当目录数超过线程池大小时，
 * 会发生所有线程都在等待最底层子目录计算结果，而最底层目录的
 * 计算任务又没有额外线程来执行，因此会出现死锁
 * java.util.concurrent.TimeoutException
 */
public class NaivlyConcurrentTotalFileSize {

	public long getTotalFileSize(File file) {
		ExecutorService service = Executors.newFixedThreadPool(10);
		try {
			return getFileSize(service, file);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			service.shutdown();
		}
		return 0;
	}

	private long getFileSize(ExecutorService service, File file) throws Exception {
		if (file.isFile()) {
			return file.length();
		}
		File[] files = file.listFiles();
		long totalSize = 0l;
		if (null != files) {
			List<Future<Long>> partFuture = new ArrayList<Future<Long>>();
			for (File f : files) {
				partFuture.add(
						service.submit(new Callable<Long>() {
							@Override
							public Long call() throws Exception {
								return getFileSize(service, f);
							}
						})
				);
			}
			for (Future<Long> f : partFuture) {
				totalSize += f.get(100, TimeUnit.SECONDS);
			}
		}
		return totalSize;
	}

	public static void main(String[] args) {
		final long start = System.currentTimeMillis();
		final long total = new NaivlyConcurrentTotalFileSize().getTotalFileSize(new File("D:\\Document"));
		final long end = System.currentTimeMillis();
		System.out.println("Total Size : " + total);
		System.out.println("Cost time : " + (end - start));
	}
}
