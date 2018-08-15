package codes.demo.proxy;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class AdviceImpl implements IAdvice {

	private String desc;

	public AdviceImpl(){}

	public AdviceImpl(String desc) {
		this.desc = desc;
	}


	@Override
	public void beforeMethod(Method method) {
		System.out.println("befor method :" + method.getName() + " say \"" + this.desc + "\"");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void afterMethod(Method method) {

		System.out.println("after method :" + method.getName() + "doing something !");

	}
}
