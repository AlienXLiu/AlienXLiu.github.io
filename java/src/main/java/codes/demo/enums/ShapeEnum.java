package codes.demo.enums;

/**
 * 计算形状面积
 */
public enum ShapeEnum {
	// 矩形
	RECTANGLE("矩形") {
		@Override
		double area(double width, double height) {
			return Math.round(width * height);
		}
	},

	// 三角形
	TRIANGLE("三角形") {
		@Override
		double area(double width, double height) {
			return Math.round(width * height * 0.5);
		}

	};

	private String shapeType;

	private ShapeEnum(String shapeType) {
		this.shapeType = shapeType;
	}

	// 计算面积
	abstract double area(double width, double height);

	@Override
	public String toString() {
		return shapeType;
	}

	public static void main(String[] args) {
		ShapeEnum[] enums = ShapeEnum.values();
		for (ShapeEnum e : enums) {
			System.out.println("name : " + e.name() + ", ShapeEnum : " + e + ", ordinal : " + e.ordinal());
		}
		System.out.println("-------------------------------");
		ShapeEnum triangle = ShapeEnum.TRIANGLE;
		ShapeEnum rectangle = ShapeEnum.RECTANGLE;
		System.out.println(triangle.toString() + "的面积：" + triangle.area(2, 3));
		System.out.println(rectangle.toString() + "的面积：" + rectangle.area(2, 3));
	}
}
