import java.io.File;
import java.util.Scanner;

public class Test extends CircularDoublyLinkedList {

	public static void main(String[] args) {

		File file = new File("test-hw1.txt");

		Scanner in = null;
		try {
			in = new Scanner(file);
		} catch (Exception e) {
			System.out.println("File not found!!");
			System.exit(0);
		}

		String l = in.nextLine();
		int n = Integer.parseInt(l);
		CircularDoublyLinkedList[] sequence = new CircularDoublyLinkedList[n];
		for (int i = 0; i < n; i++) {
			sequence[i] = new CircularDoublyLinkedList();
			insert(sequence, i, null, null);// nul yerine EMPTY de verilebilir fark eden bir durum yok boş sequenceler
											// henuz insertPos edilmedi.
		}

		while (in.hasNextLine()) {
			l = in.nextLine();
			String[] directive = l.split(" +");

			// Okuma kolayligi olmasi acisindan asagidaki satirlari belirttim.
			System.out.println();
			System.out.println(l);

			switch (directive[0]) {
			case ("insert"):
				Integer pos = Integer.parseInt(directive[1]);
				String type = directive[2];
				String seq = null;
				if (directive.length == 4) {
					seq = directive[3];
					insertPos(sequence, pos, seq, type);
				} else
					insertPos(sequence, pos, null, type);

				break;
			case ("display-all"):
				displayAll(sequence);
				break;
			case ("remove"):
				Integer p = Integer.parseInt(directive[1]);
				sequence[p].removePos(p, "remove");
				break;
			case ("display"):
				int p2 = Integer.parseInt(directive[1]);
				sequence[p2].displayPos(p2);
				break;
			case ("copy"):
				Integer c1 = Integer.parseInt(directive[1]);
				Integer c2 = Integer.parseInt(directive[2]);
				copy(sequence, c1, c2);
				break;
			case ("clip"):
				Integer pos1 = Integer.parseInt(directive[1]);
				Integer start = Integer.parseInt(directive[2]);
				Integer end = Integer.parseInt(directive[3]);
				try {
					sequence[pos1].clip(pos1, start, end);
				} catch (Exception e) {
					System.out.println("Clipping: Error limit exceeded.");
				}

				break;
			case ("max-overlap"):
				Integer overlap1 = Integer.parseInt(directive[1]);
				Integer overlap2 = Integer.parseInt(directive[2]);
				System.out.println(
						"Max-overlapping sequence between " + overlap1 + "th and " + overlap2 + "th sequence :");
				overlap(sequence, overlap1, overlap2);

				break;

			case ("transcribe"):
				Integer transc = Integer.parseInt(directive[1]);
				try {
					transcribe(sequence, transc);
				} catch (Exception e) {
					System.out.println("RNA Error");
				}

				break;
			case ("swap"):
				Integer spos1 = Integer.parseInt(directive[1]);
				Integer start1 = Integer.parseInt(directive[2]);
				Integer spos2 = Integer.parseInt(directive[3]);
				Integer start2 = Integer.parseInt(directive[4]);

				try {
					swap(sequence, spos1, start1, spos2, start2);
				} catch (Exception e) {
					System.out.println("Swap is not suitable, error!, no swap.");
				}

				break;

			}
		}
	}

}
