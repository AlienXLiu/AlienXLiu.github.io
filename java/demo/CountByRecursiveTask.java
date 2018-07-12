package demo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Created by ZhenxingLiu on 2017/9/18.
 */
public class CountByRecursiveTask {
	private final ForkJoinPool pool = new ForkJoinPool();

	class FileSizeFinder extends RecursiveTask<Long> {
		final File file;

		public FileSizeFinder(final File file) {
			this.file = file;
		}

		@Override
		protected Long compute() {
			long size = 0;
			if (file.isFile()) {
				size = file.length();
			} else {
				File[] files = file.listFiles();
				if (null != files) {
					List<ForkJoinTask<Long>> tasks = new ArrayList<ForkJoinTask<Long>>(files.length);
					for (File f : files) {
						if (f.isFile()) {
							size += f.length();
						} else {
							tasks.add(new FileSizeFinder(f));
						}
					}

					for (ForkJoinTask<Long> task : invokeAll(tasks)) {
						size += task.join();
					}

				}
			}
			return size;
		}
	}

	public static void main(String[] args) {
		final long start = System.currentTimeMillis();
		CountByRecursiveTask countByRecursiveTask = new CountByRecursiveTask();
		final long total = countByRecursiveTask.pool.invoke(countByRecursiveTask.new FileSizeFinder(new File("D:\\Document")));
		final long end = System.currentTimeMillis();
		System.out.println("Total Size : " + total);
		System.out.println("Cost time : " + (end - start));
	}
}
