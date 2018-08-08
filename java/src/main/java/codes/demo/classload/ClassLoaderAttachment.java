package codes.demo.classload;

public class ClassLoaderAttachment {

	private String desc;

	public ClassLoaderAttachment(String desc) {
		this.desc = desc;
	}

	public ClassLoaderAttachment() {
		this.desc = "default";
	}

	@Override
	public String toString() {
		return "ClassLoaderAttachment default";
	}
}
