package codes.demo.proxy.aop;

import codes.demo.proxy.IAdvice;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BeanFactory {

	private Properties properties = new Properties();

	public BeanFactory(String config) {
		getProperties(config);
	}

	public Object getBean(String className) {

		if (null == className) {
			return null;
		}

		String clzName = properties.getProperty(className);

		Object bean = null;
		try {
			bean = Class.forName(clzName).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (bean instanceof ProxyBeanFactory) {
			// 是代理类则返回相应的代理
			ProxyBeanFactory proxyBeanFactory = (ProxyBeanFactory) bean;

			try {

				// 目标
				Object target = Class.forName(properties.getProperty(className + ".target")).newInstance();

				// 公共方法
				IAdvice advice = (IAdvice) Class.forName(properties.getProperty(className + ".advice")).newInstance();

				proxyBeanFactory.setAdvice(advice);
				proxyBeanFactory.setTarget(target);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return proxyBeanFactory.proxy();
		}

		// 普通的bean直接返回
		return bean;
	}


	private void getProperties(String config) {

		InputStream inputStream = BeanFactory.class.getClassLoader().getResourceAsStream(config);

		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
