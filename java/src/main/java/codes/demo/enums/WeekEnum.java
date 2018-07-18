package codes.demo.enums;

public enum WeekEnum {
	MON("星期一"),
	TUE("星期二"),
	WED("星期三"),
	THUR("星期四"),
	FRI("星期五"),
	SATUR("星期六") {
		@Override
		protected String doSomething() {
			return "should have rest";
		}
	},
	SUN("星期日") {
		@Override
		protected String doSomething() {
			return "should have rest";
		}
	};

	WeekEnum(String desc) {
		this.desc = desc;
	}

	private String desc;

	public String getDesc() {
		return this.desc;
	}

	protected String doSomething() {
		return "should work!";
	}

	@Override
	public String toString() {
		return this.name() + " : " + this.getDesc();
	}

	public static void main(String[] args) {
		WeekEnum[] es = WeekEnum.values();
		for (WeekEnum e : es) {
			System.out.println(e.toString() + e.ordinal()  + "\t" + e.doSomething());
		}

	}
}
