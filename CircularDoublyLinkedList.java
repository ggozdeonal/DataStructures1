
public class CircularDoublyLinkedList<E> {

	private static class Node<E> {

		private E element;
		private E type;

		private Node<E> prev;
		private Node<E> next;

		public Node(E e) {
			element = e;

		}

		public E getElement() {
			return element;
		}

		public Node<E> getPrev() {
			return prev;
		}

		public Node<E> getNext() {

			return next;
		}

		public void setElement(E e) {
			element = e;
		}

		public void setPrev(Node<E> p) {
			prev = p;
		}

		public void setNext(Node<E> n) {
			next = n;
		}
	}

	private Node<E> header;

	private Node<E> trailer;

	public String type;

	private int size = 0;

	public CircularDoublyLinkedList() {
		/*
		 * header = new Node<>(null, null, null); trailer = new Node<>(null, header,
		 * null); header.setNext(trailer);
		 */
	}

	public void setType(String typeNew) {
		type = typeNew;
	}

	public void addNode(E e) {
		Node newNode = new Node(e);
		if (size == 0) {
			trailer = newNode;
			trailer.next = trailer;
			trailer.prev = trailer;
			header = trailer;

		} else {
			newNode.prev = trailer.prev;
			newNode.next = trailer;
			trailer.prev.next = newNode;
			trailer.prev = newNode;
			header = trailer;
		}
		size++;

	}

	public static void insert(CircularDoublyLinkedList[] sequence, int p, String s, String t) {
		sequence[p].type = t;
	}

	public static void insertPos(CircularDoublyLinkedList[] sequence, int p, String s, String t) {

		if (!t.equals("DNA") && !t.equals("RNA")) {
			System.out.println(t);
			return;
		}

		else if (t.equals("DNA") && s != null) {
			for (int i = 0; i < s.length(); i++)
				if (!(s.charAt(i) == 'A' || s.charAt(i) == 'T' || s.charAt(i) == 'G' || s.charAt(i) == 'C')) {
					System.out.println("Error insert command.");

					return;
				}

		} else if (t.equals("RNA") && s != null) {
			for (int i = 0; i < s.length(); i++)
				if (!(s.charAt(i) == 'A' || s.charAt(i) == 'U' || s.charAt(i) == 'G' || s.charAt(i) == 'C')) {
					System.out.println("Error insert command.");

					return;
				}

		}

		if (sequence[p].type != null && (sequence[p].type.equals("RNA") || sequence[p].type.equals("DNA"))) {
			sequence[p].removePos(p, "");
		}
		// alternatif=> sequence[p]=new CircularDoublyLinkedList ();
		sequence[p].setType(t);
		if (s != null)
			for (int j = 0; j < s.length(); j++) {

				sequence[p].addNode(s.charAt(j));

			}
		System.out.println("inserted!" + p + "th sequence.");

	}

	public void removePos(int p, String s) {
		if (this.size == 0)
			if (s.equals("remove"))
				System.out.println("There is no sequence at pos to remove.");
		// alternatif=> sequence[p] = null;
		// sequence[p].header=null;
		// sequence[p] = new CircularDoublyLinkedList();
		this.clear(this);

		this.setType("EMPTY");
		if (s.equals("remove"))
			System.out.println("removed! " + p + "th sequence");

	}

	public void clear(CircularDoublyLinkedList sequence) {
		Node<E> x = header;
		Node<E> next = null;

		int a = sequence.size;
		for (int i = 0; i < a; i++) {
			next = x.next;
			x.element = null;
			x.type = null;
			x.next = null;
			x.prev = null;
			// next=null;
			size--;
			x = next;
		}
		header = x;
		header.prev = trailer;
		trailer.next = header;

	}

	public void clip(int pos, int start, int end) throws Exception {
		if (start < 0 || end < 0 || end > this.size || start > this.size || end < start)
			throw new Exception("error");
		else if (this.header == null)
			System.out.println("No sequence!");
		int a = this.size - end;
		Node<E> x = header;
		Node<E> next = null;
		Node<E> temp = trailer.prev;
		for (int i = 0; i < start; i++) {

			next = x.next;
			x.element = null;
			x.type = null;
			x.next = null;
			x.prev = null;
			size--;
			x = next;

		}
		header = x;
		header.prev = temp;
		temp.next = header;

		Node current = header;

		int i = 0;
		while (i < end - start) {
			i++;
			current = current.next;

		}
		Node<E> temp2 = current;
		Node<E> y = current.next;

		Node<E> pre = y;

		for (int z = 0; z < a - 1; z++) {

			if (y == header) {

				break;
			}

			pre = y.next;

			y.element = null;
			y.type = null;
			y.next = null;
			y.prev = null;
			size--;
			y = pre;

		}
		header.prev = temp2.next;
		temp2.next = header;
		// => yapip yapmamak fark etmez trailer=current;
		// trailer=header;
		System.out.println("Clipped " + pos + "th sequence.");

	}

	public static void overlap(CircularDoublyLinkedList[] s, int p1, int p2) {
		if (!(s[p1].type.equals(s[p2].type))) {
			System.out.println("No overlap because of types");
			return;
		}
		String s1 = "";
		String s2 = "";
		Node current = s[p1].header;

		for (int i = 0; i < s[p1].size; i++) {
			s1 += current.element;
			current = current.next;
		}
		current = s[p2].header;
		for (int i = 0; i < s[p2].size; i++) {
			s2 += current.element;
			current = current.next;
		}

		String overlap = "";
		int a = s1.length();
		int b = s2.length();
		int c = 0;

		String s_1 = "";
		String s_2 = "";
		if (a > b) {
			c = b;
			s_1 = s1.substring(a - b);
			s_2 = s2;

		}

		else if (b > a) {
			c = a;
			s_2 = s2.substring(0, a);
			s_1 = s1;

			for (int i = c; i >= 0; i--) {

				if (s_2.substring(0, i).equals(s_1.substring(b - i - 1))) {

					overlap = s_2.substring(0, i);
					break;
				}
			}
		} else {
			c = a;
			s_2 = s2;
			s_1 = s1;

			for (int i = c; i >= 0; i--) {

				if (s_2.substring(0, i).equals(s_1.substring(b - i))) {

					overlap = s_2.substring(0, i);
					break;
				}
			}
		}
		if (overlap.length() == 0)
			System.out.println("No overlap");
		else
			System.out.println("Max-Overlap:" + overlap.length() + "  " + overlap);

	}

	public static void swap(CircularDoublyLinkedList[] s, int p1, int s1, int p2, int s2) throws Exception {

		if (s1 > s[p1].size || s1 < 0 || s2 < 0 || s2 > s[p2].size || !(s[p1].type.equals(s[p2].type))
				|| s[p1].header == null || s[p2].header == null)
			throw new Exception("error");

		CircularDoublyLinkedList temp1 = new CircularDoublyLinkedList();
		CircularDoublyLinkedList temp2 = new CircularDoublyLinkedList();
		temp1.setType(s[p1].type);
		temp2.setType(s[p2].type);

		Node current = s[p1].header;

		for (int i = 0; i < s1; i++) {
			temp1.addNode(current.element);
			current = current.next;
		}

		Node current2 = s[p2].header;
		int i = 0;
		for (i = 0; i < s2; i++) {

			current2 = current2.next;
		}

		for (i = i; i < s[p2].size; i++) {

			temp1.addNode(current2.element);
			current2 = current2.next;
		}

		current = s[p2].header;

		for (i = 0; i < s2; i++) {
			temp2.addNode(current.element);
			current = current.next;
		}

		current2 = s[p1].header;

		for (i = 0; i < s1; i++) {

			current2 = current2.next;
		}

		for (i = i; i < s[p1].size; i++) {

			temp2.addNode(current2.element);
			current2 = current2.next;
		}

		s[p2] = temp2;
		s[p1] = temp1;

		// Asagidaki kod tailleri degistirerek swap islemi yapıyor cogu ornekte calisti
		// fakat
		// emin olmadigim icin boyle bir alternatif kod yazdim ikisinin de islevi ayni.

		/*
		 * Node current = s[p1].header;
		 * 
		 * 
		 * current = s[p1].header;
		 * 
		 * for (int i = 0; i < s1; i++) { current = current.next; }
		 * 
		 * Node current2 = s[p2].header;
		 * 
		 * for (int i = 0; i < s2; i++) { current2 = current2.next; }
		 * 
		 * Node t1= current2.prev; Node t2=current.next; Node t3=s[p1].header.prev; Node
		 * t4=s[p2].header;
		 * 
		 * current.next = current2; current2.prev = current; s[p2].header.prev.next =
		 * s[p1].header; s[p1].header.prev = s[p2].header.prev;
		 * 
		 * 
		 * t1.next=t2; t2.prev=t1; t4.prev=t3; t3.next=t4;
		 */

		System.out.println("swapped " + p1 + " from index " + s1 + " with " + p2 + "th sequence from index " + s2);
	}

	public static void transcribe(CircularDoublyLinkedList[] sequence, int p) throws Exception {
		if (sequence[p].type != null && sequence[p].type.equals("RNA")) {

			throw new Exception("RNA");
		} else if (sequence[p].header == null) {
			System.out.println("Slot error");
			return;
		}
		sequence[p].setType("RNA");
		Node current = sequence[p].header;
		for (int i = 0; i < sequence[p].size; i++) {

			if (current.element.equals('T'))
				current.setElement('U');
			current = current.next;
		}
		current = sequence[p].header;
		for (int i = 0; i < sequence[p].size; i++) {

			if (current.element.equals('U'))
				current.setElement('A');

			else if (current.element.equals('A'))
				current.setElement('U');

			else if (current.element.equals('G'))
				current.setElement('C');

			else if (current.element.equals('C'))
				current.setElement('G');

			current = current.next;

		}

		CircularDoublyLinkedList temp = new CircularDoublyLinkedList();
		temp.setType("RNA");
		current = current.prev;

		for (int i = 0; i < sequence[p].size; i++) {

			temp.addNode(current.element);
			current = current.prev;

		}

		sequence[p] = temp;

	}

	public static void copy(CircularDoublyLinkedList[] sequence, int pos1, int pos2) {
		if (sequence[pos1].header == null) {
			System.out.println("copy is not suitable");
			return;
		}

		CircularDoublyLinkedList temp = new CircularDoublyLinkedList();

		if (sequence[pos2].type != null)
			if (sequence[pos2].type.equals("RNA") || sequence[pos2].type.equals("DNA")) {
				temp = sequence[pos2];
				temp.type = "";
			}

		Node current = sequence[pos1].header;
		if (sequence[pos2].type != null)
			sequence[pos2].removePos(pos2, "");
		sequence[pos2].setType(sequence[pos1].type);

		for (int i = 0; i < sequence[pos1].size; i++) {

			sequence[pos2].addNode(current.element);
			current = current.next;
		}
		// temp.clear(temp);
		temp = null;// her iki satir da garbage işlevi görür
		sequence[pos2].setType(sequence[pos1].type);
		System.out.println("copied " + pos1 + " to " + pos2);

	}

	public void insert(int position, E e, E t) {
		if (position > size) {
			System.out.println("error");
			return;
		}
		Node newNode = new Node(e);
		Node current = header;
		int i = 0;
		while (i < position) {
			i++;
			current = current.next;
		}
		current.element = e;
		current.type = t;

	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public static void displayAll(CircularDoublyLinkedList[] sequence) {

		if (sequence.length == 0) {
			System.out.println("data is empty");

		} else {

			int count = 0;
			for (count = 0; count < sequence.length; count++) {
				sequence[count].displayPos(count);
				System.out.println();
			}
		}
	}

	public void displayPos(int pos) {

		if (this.type == null || this.type.equals("EMPTY")) {
			System.out.println(pos + ".th:   EMPTY SLOT");

		}

		else {
			Node current = header;
			int count = 0;
			System.out.print(pos + ".th:" + this.type + "   (");
			if (this.header != null)
				while (true) {

					System.out.print(current.element);
					current = current.next;
					count++;
					if (current == header)
						break;

				}
			System.out.println(")");
		}
	}
}
