
public class HuffmanNode implements Comparable<HuffmanNode> {

	protected int frequency;
	protected char character;
	protected HuffmanNode left;
	protected HuffmanNode right;
	
	public HuffmanNode(int frequency, char character) { 
		this.frequency = frequency;
		this.character = character;
	}
	
	public HuffmanNode(int frequency) {
		this.frequency = frequency;
	}

	@Override
	public int compareTo(HuffmanNode other) {
		return this.frequency - other.frequency;
	}

	@Override
	public String toString() {
		return "HuffmanNode [frequency=" + frequency + ", character=" + character + "]";
	}
	
}
