package fourth_chapter;

import java.util.concurrent.TimeUnit;

public class Profiler {

	static final ThreadLocal<Long> TIME = new ThreadLocal<Long>() {
		@Override
		protected Long initialValue() {
			return System.currentTimeMillis();
		}
	};

	public static final void begin() {
		TIME.set(System.currentTimeMillis());
	}

	public static final long end() {
		return System.currentTimeMillis() - TIME.get();
	}


	public static void main(String[] args) throws Exception {
		Profiler.begin();
		TimeUnit.SECONDS.sleep(1);
		System.out.println("Profiler cost :" + Profiler.end());
	}

}
