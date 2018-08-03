package codes.demo.sort;

/**
 * 一副从1到n的牌，每次从牌堆顶取一张放桌子上，再取一张放牌堆底，
 * 直到手里没牌，最后桌子上的牌是从1到n有序，设计程序，输入n，输出牌堆的顺序数组
 * @param <E>
 */
public class SafeSort<E> {

	// 数组
	private E[] elements;

	// 元素个数
	private volatile int count;

	// 移除并且返回当前元素
	public E poll(){

		return null;
	}

	// 移除所有元素
	public E[] pollAll() {

		return null;
	}


	// 移除元素后把next放置到原数组末尾
	private void setToTail(){

	}

}
