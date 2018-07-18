package codes.demo.singleton;

import java.util.concurrent.TimeUnit;

public class NormalBean implements Comparable<NormalBean> {

	private String desc;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public NormalBean() {
		try {
			TimeUnit.MILLISECONDS.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.desc = "描述";
		System.out.println("NormalBean 初始化!");
	}

	@Override
	public int compareTo(NormalBean o) {
		if (null == o || this == null) {
			return -1;
		}
		if (this == o) {
			return 0;
		} else {
			return this.hashCode() > o.hashCode() ? 1 : -1;
		}
	}

	@Override
	public String toString() {
		return "NormalBean[ " + desc + "]";
	}
}
