package codes.demo.catchfinally;

public class BasicCatchTest {

	/**
	 * 基本类型
	 * <p>
	 * catch...finally 示例
	 */

	public static void main(String[] args) {
		int p = 0;
		int a = test(p);
		System.out.println("print in main, result of p  : " + p);
		System.out.println("print in main, result of test : " + a);
	}

	public static int test(int p) {

		try {

			return p = 1 / p;

		} catch (Exception e) {

			p = -1;
			System.out.println("----print in catch---- P : " + p);
			return p; // 退出
		} finally {
			p = -2;
			System.out.println("----print in finally---- P : " + p);
		}

	}
}
