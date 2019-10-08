package com.bjsxt.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestSockClient {
	public static void main(String[] args) {
		InputStream is = null;
		OutputStream os = null;
		try {
			Socket socket = new Socket("localhost", 5888);
			is = socket.getInputStream();
			os = socket.getOutputStream();
			DataInputStream dis = new DataInputStream(is);
			DataOutputStream dos = new DataOutputStream(os);
			dos.writeUTF("hey");
			String s = null;
			if ((s = dis.readUTF()) != null)
				;
			System.out.println(s);
			dos.close();
			dis.close();
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
