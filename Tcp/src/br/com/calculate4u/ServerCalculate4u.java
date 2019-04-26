package br.com.calculate4u;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import br.com.calculate4u.parser.CalculatorParser;

public class ServerCalculate4u {

	int PORT = 54321;
	ServerSocket servidor;
	ObjectOutputStream output;
	boolean executando;
	MessageDigest messageDigest;
	int tokenCount;

	public ServerCalculate4u() throws Exception {
		executando = true;
		servidor = new ServerSocket(PORT);
		messageDigest = MessageDigest.getInstance("MD5");
		tokenCount = 1;
	}

	public void startListening() throws Exception {
		while (executando) {
			Socket cliente = servidor.accept();
			new Thread(new Runnable() {
				@Override
				public void run() {
					comunicacao(cliente);
				}
			}).start();
		}
		servidor.close();
	}

	public void comunicacao(Socket cliente) {
		Map<String, String> tokens = new HashMap<String, String>();
		try {
			do {
				byte request[] = new byte[1024];
				cliente.getInputStream().read(request);
				String command = new String(request);

				if (command.startsWith("is available")) {
					System.out.println("connection request");
					cliente.getOutputStream().write("yes".getBytes());

				} else if (command.startsWith("solve")) {
					String operacao = command.split(" ")[1];
					CalculatorParser calculatorParser = new CalculatorParser();
					String solucao = calculatorParser.parser(operacao);
					String token = "" + tokenCount++;
					messageDigest.update(token.getBytes());
					token = DatatypeConverter.printHexBinary(messageDigest.digest());
					tokens.put(token, solucao);
					String sendSize = token + ";" + solucao.length();
					System.out.println("solve request: " + operacao);
					cliente.getOutputStream().write(sendSize.getBytes());

				} else if (command.startsWith("get")) {
					String getToken = command.split(" ")[1];
					String tokenKey = null;
					String solucao = null;
					for (Object keys : tokens.keySet().toArray()) {
						tokenKey = (String) keys;
						if (getToken.equalsIgnoreCase(getToken)) {
							solucao = tokens.get(tokenKey);
							break;
						}
					}
					System.out.println("get request: " + tokenKey);
					cliente.getOutputStream().write(solucao.getBytes());
					tokens.remove(tokenKey);

				} else if (command.startsWith("end")) {
					System.out.println("end request");
					break;
				}

			} while (true);
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
		try {
			cliente.close();
		} catch (IOException e) {
			System.out.println(e.getStackTrace());
		}
	}
}
