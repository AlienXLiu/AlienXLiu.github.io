package codes.demo;

public class AVLDemo {
	public Node root;

	public class Node {

		Node left;
		Node right;
		int value;
		int height;

		public Node(int value) {
			this(value, null, null);
		}

		public Node(int value, Node leftNode, Node rightNode) {
			this.value = value;
			this.left = leftNode;
			this.right = rightNode;
			this.height = 0;
		}
	}

	private int getHeight(Node node) {
		return node == null ? -1 : node.height;
	}

	/**
	 * 插入
	 *
	 * @param node
	 */
	public Node insert(int value, Node node) {
		if (node == null) {
			return new Node(value);
		}
		if (value < node.value) {
			node.left = insert(value, node.left);
			if (getHeight(node.left) - getHeight(node.right) > 1) {
				if (value < node.left.value) {
					// 单旋
					node = right(node);
				} else if (value > node.left.value) {
					// 双旋
					node = doubleRight(node);
				}
			}
		}

		if (value > node.value) {
			node.right = insert(value, node.right);
			if (getHeight(node.right) - getHeight(node.left) > 1) {
				if (value > node.right.value) {
					// 单旋
					node = left(node);
				} else if (value < node.right.value) {
					// 双旋
					node = doubleLeft(node);
				}
			}
		}

		node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
		return node;
	}

	public Node left(Node node) {
		Node tmpNode = node.right;
		node.right = tmpNode.left;
		tmpNode.left = node;
		tmpNode.height = Math.max(getHeight(tmpNode.left), getHeight(node.right)) + 1;
		node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
		return tmpNode;
	}

	public Node right(Node node) {

		Node tmpNode = node.left;
		node.left = tmpNode.right;
		tmpNode.right = node;
		tmpNode.height = Math.max(getHeight(tmpNode.left), getHeight(node.right)) + 1;
		node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
		return tmpNode;
	}

	private Node doubleLeft(Node node) {
		node.right = right(node.right);
		return left(node);
	}

	private Node doubleRight(Node node) {
		node.left = left(node.left);
		return right(node);
	}

	/**
	 * 创建
	 */
	public void create(int[] data) {

		for (int value : data) {
			root = insert(value, root);
		}
		System.out.println();

	}

	public static void main(String[] args) {
		int[] data = {2, 6, 8, 4, 12, 45, 25, 14, 28};

		AVLDemo d = new AVLDemo();
		d.create(data);
		System.out.println();
	}
}
