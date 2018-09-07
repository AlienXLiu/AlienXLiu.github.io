package codes.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ArrayClass {

	public static void main(String[] args) {
		Collection collections = new ArrayList();


		List l = new ArrayList();
		String[] elementData = new String[]{"1", "2", "3", "4", "5"};
		String[] e1 = new String[5];
		int index = 1;
		int numMoved = elementData.length - index - 1;
		int len = elementData.length;
		System.arraycopy(elementData, index + 1, e1, 0,
				3);
		System.arraycopy(elementData, index + 1, elementData, index,
				numMoved);

		//elementData[--len] = null;
		System.out.println(Arrays.asList(elementData));
		System.out.println(Arrays.asList(e1));

	}

	class Ref {

		private String desc;

		public Ref() {
		}

		public Ref(String desc) {
			this.desc = desc;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}


	}


}
