package codes.demo.annotation;


import java.lang.reflect.Method;

@MyAnnotation(index = 1, arr = "1")
public class AnnotationTest {

	public static void main(String[] args) {
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
	}

	@MyAnnotation(index = 2, arr = {"main", "print"}, desc = "I am print" , meta = @MetaAnnotation("set Meta Print"))
	public void print() {
		System.out.println("AnnotaionTest print!");
	}
}
