package codes.demo.singleton;

public enum SingletonByEnum {

	//枚举元素本身就是单例
	INTANCE;

	private NormalBean bean;

	SingletonByEnum() {
		System.out.println();
	}

	//添加自己需要的操作
	public void singletonOperation() {

	}




}
