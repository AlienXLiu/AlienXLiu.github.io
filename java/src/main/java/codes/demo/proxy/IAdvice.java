package codes.demo.proxy;

import java.lang.reflect.Method;

public interface IAdvice {

	void beforeMethod(Method method);

	void afterMethod(Method method);
}
