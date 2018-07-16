package codes.demo.file;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by ZhenxingLiu on 2017/9/14.
 */
public class CountFileParallel {

	class Subdirectory {
		final public long fileSize;
		final public List<File> directory;

		public Subdirectory(long size, List<File> directory) {
			this.fileSize = size;
			this.directory = Collections.unmodifiableList(directory);
		}
	}

	private Subdirectory getSubdirectoryInfo(final File file) {
		long fileSize = 0l;
		final List<File> dircectory = new ArrayList<>();
		if (file.isDirectory()) {
			final File[] children = file.listFiles();
			if (children != null) {
				for (File f : children) {
					if (f.isFile()) {
						fileSize += f.length();
					} else {
						dircectory.add(f);
					}
				}
			}

		}
		return new Subdirectory(fileSize, dircectory);
	}

	public long getTotalFileSize(final File file) {
		final ExecutorService service = Executors.newFixedThreadPool(10);
		long total = 0;
		try {
			List<File> fileList = new ArrayList<File>();
			fileList.add(file);
			while (!fileList.isEmpty()) {
				List<Future<Subdirectory>> futures = new ArrayList<>();
				for (final File f : fileList) {
					futures.add(service.submit(new Callable<Subdirectory>() {
						@Override
						public Subdirectory call() throws Exception {
							return getSubdirectoryInfo(f);
						}
					}));
				}
				fileList.clear();
				for (Future<Subdirectory> future : futures) {
					Subdirectory s = future.get(10, TimeUnit.SECONDS);
					total += s.fileSize;
					fileList.addAll(s.directory);
				}
			}


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			service.shutdown();
		}
		return total;
	}

	public static void main(String[] args) {
		final long start = System.currentTimeMillis();
		final long total = new CountFileParallel().getTotalFileSize(new File("D:\\Document"));
		final long end = System.currentTimeMillis();
		System.out.println("Total Size : " + total);
		System.out.println("Cost time : " + (end - start));
	}

}
