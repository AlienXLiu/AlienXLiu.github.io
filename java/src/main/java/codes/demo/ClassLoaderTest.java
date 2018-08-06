package codes.demo;

/**
 *  Java 虚拟机默认三个类加载器：
 *  	1、BootStrap ： 不是java类，顶级类加载器；加载范围：JRE/lib/rt.jar
 *  	2、ExtClassLoader ：父加载器为BootStrap；加载范围：JRE/lib/ext/*.jar
 *  	3、AppClassLoader ：父加载器为ExtClassLoader；加载范围：CLASSPATH指定的所有JAR或目录
 *
 *	类加载器的委托机制：
 *		1、当java虚拟机要加载一个类时，首先当前线程的类加载器去加载线程中的第一个类，如果类A中引用了类B，
 *		   JAVA虚拟机将使用类加载类A的类装载器来加载类B；还可以直接调用ClassLoader.loadClass()方法
 *		   来指定某个类加载器去加载某个类。
 *		2、每个类加载器加载类时，先委托给其上级类加载器，当所有父加载器没有加载到类，回到发起者类加载器，
 *		   如果还没有加载到则抛ClassNotFoundException.
 *
 */

public class ClassLoaderTest {
	public static void main(String[] args) {
		String name = ClassLoaderTest.class.getClassLoader().getClass().getName();

		System.out.println(name);
	}
}
