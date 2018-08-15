package codes.demo.proxy;

import codes.demo.proxy.aop.BeanFactory;

import java.util.Collection;

public class ProxyTest {

	public static void main(String[] args) {
//		MyProxy proxy = new MyProxy();
//
//		// target : 目标，具体的业务实现
//		ArrayList target = new ArrayList();
//
//		// advice : 公用逻辑， 例如日志，数据库链接开启、释放
//		IAdvice advice = new AdviceImpl("hello, guys !");
//
//		// proxy : 代理
//		Collection list = (Collection) proxy.proxy(target, advice);
//
//		// 调用代理类方法
//		list.add("123");

		BeanFactory beanFactory = new BeanFactory("application.properties");
		Collection obj = (Collection) beanFactory.getBean("demo.proxy");
		obj.add("123123");
		obj.add("111");
		obj.add("22222");
		System.out.println(obj.size());
	}
}
