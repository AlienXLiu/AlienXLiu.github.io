package codes.demo.classload;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MyClassLoader extends ClassLoader {

	private String classPath;

	public MyClassLoader(String classPath) {
		this.classPath = classPath;
	}


	@Override
	protected Class<?> findClass(String className) throws ClassNotFoundException {
		String path = classPath + File.separator + className + ".class";
		FileInputStream in = null;
		ByteArrayOutputStream out = null;
		try {
			in = new FileInputStream(path);
			out = new ByteArrayOutputStream();
			// 混淆
			cypher(in, out);
			byte[] clzBytes = out.toByteArray();
			return defineClass(null, clzBytes, 0, clzBytes.length);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return super.findClass(className);
	}

	// 简单的类加密
	public static void cypher(InputStream in, OutputStream out) {
		int len = -1;
		if (in == null || out == null) {
			return;
		}
		try {
			while ((len = in.read()) != -1) {
				out.write(len ^ 0xff);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
