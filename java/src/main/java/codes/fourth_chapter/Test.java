package codes.fourth_chapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test {

	static final Logger logger = LoggerFactory.getLogger("ALL");

	public static void main(String[] args) throws Exception {
		int a = 2;
		int b = 1;
		if (a > b || (--a==b)) {
			logger.info("123");
		}
		System.out.println(a);
		System.out.println(b);
	}


}
