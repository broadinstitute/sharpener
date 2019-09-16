package sharpener;

import scala.util.Random;

public class IdGenerator {

	private final int idLength;
	
	private final Container<String> container;
	
	public IdGenerator(int idLength, Container<String> container) {
		this.idLength = idLength;
		this.container = container;
	}
	
	public synchronized String nextId() {
		String id = randString(idLength);
		while (container.contains(id)) {
			id = randString(10);
		}
		return id;
	}
	
	// STATIC
	
	private static Random rand = new Random();


	private static String randString(int len) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			sb.append(rand.scala$util$Random$$nextAlphaNum$1());
		}
		return sb.toString();
	}
}
