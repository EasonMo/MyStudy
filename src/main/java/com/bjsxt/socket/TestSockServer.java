package com.bjsxt.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TestSockServer {
	public static void main(final String[] args) {
		InputStream in = null;
		OutputStream out = null;
		try {
			final ServerSocket ss = new ServerSocket(5888);
			final Socket socket = ss.accept();
			in = socket.getInputStream();
			out = socket.getOutputStream();
			final DataOutputStream dos = new DataOutputStream(out);
			final DataInputStream dis = new DataInputStream(in);
			String s = null;
			if ((s = dis.readUTF()) != null) {
				System.out.println(s);
				System.out.println("from: " + socket.getInetAddress());
				System.out.println("Port: " + socket.getPort());
			}
			dos.writeUTF("hi，hello");
			dis.close();
			dos.close();
			socket.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
}