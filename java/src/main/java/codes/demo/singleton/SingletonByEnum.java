package codes.demo.singleton;

/**
 * 利用枚举即能保证线程安全也可以防止反射破坏
 */
public enum SingletonByEnum {

	//枚举元素本身就是单例
	INTANCE;

	private NormalBean bean;

	SingletonByEnum() {
		System.out.println("Enum 初始化！");
		bean = new NormalBean();
	}

	/**
	 *
	 * @return
	 */
	public NormalBean getInstance() {
		return bean;
	}

}
