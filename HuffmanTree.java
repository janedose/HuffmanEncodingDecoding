import java.io.PrintStream;
import java.util.*;

public class HuffmanTree {
	HuffmanNode root = null;
	HuffmanNode superRoot = null;
	Queue<HuffmanNode> pq2 = new PriorityQueue<>();
	String encode = "";
	/**
	 * create the tree of frequencies
	 * @param count
	 */
	public HuffmanTree(int[] count) {
		Queue<HuffmanNode> pq = new PriorityQueue<>();
		for (int i = 0; i < count.length; i ++) {
			if (count[i] > 0) {
				pq.add(new HuffmanNode (count[i], (char)i));
			}
		}
		//add an eof character 256
		HuffmanNode eof = new HuffmanNode (1, (char)MakeCode.CHAR_MAX);
		eof.left = null;
		eof.right = null;
		pq.add(eof);
		//combining the first 2 nodes in pq into another branch for the pq
		while (pq.size() > 1) {
			HuffmanNode leftNode = pq.poll();
			HuffmanNode rightNode = pq.poll();
			HuffmanNode branch = new HuffmanNode (leftNode.frequency+ rightNode.frequency);
			branch.left = leftNode;
			branch.right = rightNode;
			pq.add(branch);
			root = branch;
		}
	}
	/**
	 * create final HuffmanTree for previously created code file
	 * @param input
	 */
	public HuffmanTree(Scanner input) {
		superRoot = new HuffmanNode(-1);
		pq2.add(superRoot);
		while(input.hasNextLine()) {
			int n = Integer.parseInt(input.nextLine());		//the character
			String code = input.nextLine();		//the code to the character
			char[] arr = code.toCharArray();
			HuffmanNode currentNode = pq2.peek();
			for (int i = 0; i < arr.length; i++) {
				int a = Character.getNumericValue(arr[i]);
				if (a == 0) {		//0 for left
					if (i == arr.length - 1) {
						if (currentNode.left == null) {
							currentNode.left = new HuffmanNode(-1, (char) n);
						}
					} else {
						if (currentNode.left == null) {
							currentNode.left = new HuffmanNode(-1);
						}
						currentNode = currentNode.left;
					}
				} else {		//1 for right
					if (i == arr.length - 1) {
						if (currentNode.right == null) {
							currentNode.right = new HuffmanNode(-1, (char) n);
						}
					} else {
						if (currentNode.right == null) {
							currentNode.right = new HuffmanNode(-1);
						}
						currentNode = currentNode.right;
					}				
				}
			}
		}
	}
	
	public void decode(BitInputStream input, PrintStream output, int eof) {
		int bit = -2;
		HuffmanNode currentNode = pq2.peek();
		while(bit != -1) {
			bit = input.readBit();
			if (bit == 0) {		//0 for left
				currentNode = currentNode.left;
				if (currentNode.left == null && currentNode.right == null) {
					output.write(currentNode.character);
					currentNode = superRoot;
				} 
			} else if (bit == 1) {		//1 for right
				currentNode = currentNode.right;
				if (currentNode.left == null && currentNode.right == null) {
					output.write(currentNode.character);
					currentNode = superRoot;
				}
			}
		} 
	}

	/**
	 * helper for the write(output) method
	 * @param root
	 * @param s
	 * @return
	 */
	public String printCode(HuffmanNode root, String s) { 
		String code = "";
		if (root.left == null && root.right == null) { 
			code = (int)root.character + "\n" + s;
			return encode += code + "\n"; 
		}
		printCode(root.left, s + "0"); 
		printCode(root.right, s + "1"); 
		return encode;
	} 

	public void write(PrintStream output) {
		output.print(printCode(root, ""));
	}
}
