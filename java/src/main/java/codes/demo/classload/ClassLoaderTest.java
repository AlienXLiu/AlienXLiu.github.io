package codes.demo.classload;

/**
 * Java 虚拟机默认三个类加载器：
 * 1、BootStrap ： 不是java类，顶级类加载器；加载范围：JRE/lib/rt.jar
 * 2、ExtClassLoader ：父加载器为BootStrap；加载范围：JRE/lib/ext/*.jar
 * 3、AppClassLoader ：父加载器为ExtClassLoader；加载范围：CLASSPATH指定的所有JAR或目录
 * <p>
 * 类加载器的委托机制：
 * 1、当java虚拟机要加载一个类时，首先当前线程的类加载器去加载线程中的第一个类，如果类A中引用了类B，
 * JAVA虚拟机将使用类加载类A的类装载器来加载类B；还可以直接调用ClassLoader.loadClass()方法
 * 来指定某个类加载器去加载某个类。
 * 2、每个类加载器加载类时，先委托给其上级类加载器，当所有父加载器没有加载到类，回到发起者类加载器，
 * 如果还没有加载到则抛ClassNotFoundException.
 */

public class ClassLoaderTest {


	public static void main(String[] args) throws Exception {
		String clzPath = "D:\\Application_WorkSpace\\GIT\\AlienXLiu.github.io\\AlienXLiu.github.io\\java\\target\\classes\\codes\\demo\\classload\\ClassLoaderAttachment.class";
		String desPath = "D:\\Application_WorkSpace\\GIT\\AlienXLiu.github.io\\AlienXLiu.github.io\\java\\lib";
//		String desClzName = clzPath.substring(clzPath.lastIndexOf("\\") + 1);
//		FileInputStream in = new FileInputStream(clzPath);
//		FileOutputStream out = new FileOutputStream(desPath + "\\" + desClzName);
//		MyClassLoader.cypher(in, out);
//
//
//		in.close();
//		out.close();

		Class clz1 = ClassLoaderTest.class.getClassLoader().loadClass("codes.demo.classload.ClassLoaderAttachment");
		Object obj1 = clz1.newInstance();
		System.out.println(obj1.toString());

		System.out.println("-------------华丽的分割线----------------");

		Class clz = new MyClassLoader(desPath).loadClass("ClassLoaderAttachment");
		Object obj = clz.newInstance();
		System.out.println(obj.toString());
	}


}
