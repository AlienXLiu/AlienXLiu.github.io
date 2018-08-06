package codes.demo.annotation;


import java.lang.reflect.Method;
import java.util.ArrayList;

@MyAnnotation(index = 1, arr = "1")
public class AnnotationTest {

	public static void main(String[] args) throws Exception {
		boolean isAnnotation = AnnotationTest.class.isAnnotationPresent(MyAnnotation.class);
		if (isAnnotation) {
			MyAnnotation annotation = AnnotationTest.class.getAnnotation(MyAnnotation.class);
			System.out.println(annotation.index());
			System.out.println(annotation.desc());
			System.out.println(annotation.arr());
			System.out.println(annotation.meta().value());
		}

		Method[] methods = AnnotationTest.class.getMethods();
		for (Method method : methods) {
			MyAnnotation annotation = method.getAnnotation(MyAnnotation.class);
			if (null == annotation) continue;
			System.out.println(annotation.index());
			System.out.println(annotation.desc());
			System.out.println(annotation.arr());
			System.out.println(annotation.meta().value());
		}

		ArrayList<String> list1 = new ArrayList<String>(10);
		ArrayList<Integer> list2 = new ArrayList<Integer>(10);
		list1.add("A");
		list1.add("B");
		// 编译之后泛型(参数类型)会被去掉, list1中可以add其它类型.
		list1.getClass().getMethod("add", Object.class).invoke(list1, 123);

		System.out.println("List1大小 ：" + list1.size());
		System.out.println("List1与List2 类型是否相等：" + (list1.getClass() == list2.getClass()));
	}

	@MyAnnotation(index = 2, arr = {"main", "print"}, desc = "I am print", meta = @MetaAnnotation("set Meta Print"))
	public void print() {
		System.out.println("AnnotaionTest print!");
	}
}
