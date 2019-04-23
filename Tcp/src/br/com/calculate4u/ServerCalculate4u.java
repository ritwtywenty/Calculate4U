package br.com.calculate4u;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerCalculate4u {
	
	int PORT = 54321;
	ServerSocket servidor;
	ObjectOutputStream output;
	
	public ServerCalculate4u() throws IOException {
		servidor = new ServerSocket(PORT);
		
	}
	
	public void acceptConection() throws Exception{
		Socket cliente = servidor.accept();
		//output = new ObjectOutputStream(cliente.getOutputStream());
		
		//ObjectInputStream input = new ObjectInputStream(cliente.getInputStream());
		
		byte data[] = new byte [1024];
		
		cliente.getInputStream().read(data);
		String recebido = new String(data);
		System.out.println(recebido);
		
		data = "msg recebida".getBytes();
		cliente.getOutputStream().write(data);
		
		//input.close();
		//output.close();
		cliente.close();
		servidor.close();
	}
	
	  public static void main(String[] args) {
		  try {
			ServerCalculate4u calculate4u = new ServerCalculate4u();
			try {
				calculate4u.acceptConection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	  }     
	}
