package codes.demo.file;

import java.io.File;

/**
 * Created by ZhenxingLiu on 2017/9/14.
 */
public class CountFileDriect {

	public long countFileSileInDir(final File file) {
		if (file.isFile()) {
			return file.length();
		}
		final File[] files = file.listFiles();
		long total = 0l;
		if (null != files) {
			for (File f : files) {
				total += countFileSileInDir(f);
			}
		}
		return total;
	}

	public static void main(String[] args) {
		final long start = System.currentTimeMillis();
		final long total = new CountFileDriect().countFileSileInDir(new File("D:\\Document"));
		final long end = System.currentTimeMillis();
		System.out.println("Total Size : " + total);
		System.out.println("Cost time : " + (end - start));
	}


}
