package codes.demo.singleton;

public class NormalBean {

	private String desc;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "NormalBean[ " + desc + "]";
	}
}
