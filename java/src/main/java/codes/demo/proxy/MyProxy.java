package codes.demo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyProxy {

	public Object proxy(final Object target, final IAdvice advice) {

		Object reult = Proxy.newProxyInstance(
				// 类加载器
				target.getClass().getClassLoader(),
				// 接口
				target.getClass().getInterfaces(),
				// handler
				new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						System.out.println("method : " + method.getName());

						advice.beforeMethod(method);

						Object rs = method.invoke(target, args);

						advice.afterMethod(method);

						return rs;
					}
				}

		);

		return reult;

	}


}
