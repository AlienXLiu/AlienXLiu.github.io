package codes.demo.catchfinally;

public class ReferenceCatchTest {
	/**
	 * 引用类型
	 * <p>
	 * catch...finally 示例
	 */
	public static void main(String[] args) {

		A a = new A();
		a.setName("set in main");
		test(a);
		System.out.println(a);
	}

	public static void test(A a) {
		try {
			if (1 / 0 > 1) {
				a.setName("normal");
			}
			return;
		} catch (Exception e) {
			a.setName("set in catch");
			System.out.println("------Print in catch-------");
			return;
		} finally {
			a.setName("set in finally");
			System.out.println("------Print in finally-------");
		}

	}

	static class A {
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "[ class A - name : " + name + " ]";
		}
	}

}
