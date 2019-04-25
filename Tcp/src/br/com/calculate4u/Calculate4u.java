package br.com.calculate4u;

public class Calculate4u {
	public static void main(String[] args) throws Exception {
		System.out.println("Calculate4U server running " + System.currentTimeMillis()+ "...");
		ServerCalculate4u calculate4u = new ServerCalculate4u();
		calculate4u.startListening();
	}
}
