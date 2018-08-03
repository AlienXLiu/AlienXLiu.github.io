package codes.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface MyAnnotation {

	int index(); // 基础数据类型

	String[] arr(); // 数组

	String desc() default "默认名字"; // 字符串并给默认值

	MetaAnnotation meta() default @MetaAnnotation("Meta默认值");
}
