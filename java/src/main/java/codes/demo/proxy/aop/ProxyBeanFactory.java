package codes.demo.proxy.aop;

import codes.demo.proxy.IAdvice;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyBeanFactory {

	private Object target;

	private IAdvice advice;


	public Object proxy() {

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


	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

	public IAdvice getAdvice() {
		return advice;
	}

	public void setAdvice(IAdvice advice) {
		this.advice = advice;
	}
}
