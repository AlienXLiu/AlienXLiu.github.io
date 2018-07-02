package fourth_chapter.threadpool;

public class JobTest {
	public static void main(String[] args) {
		DefaultThreadPool pool = new DefaultThreadPool(3);
		for (int i = 0; i < 1000; i++) {
			pool.execute(new Runnable() {
				@Override
				public void run() {
					System.out.println(Thread.currentThread().getName());
				}
			});
		}
		pool.shutdown();
	}
}
