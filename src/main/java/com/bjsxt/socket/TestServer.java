package com.bjsxt.socket;

/*	范例名称：简单的client/server程序
 * 	源文件名称：TestClient.java/TestServer.java
 *	要  点：
 *		1. Java Socket编程步骤
 *		2. Socket/ServerSocket类用法
 *		3. 通过Socket对象可以获取通信对方Socket的信息
 */
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer {
	public static void main(final String args[]) {
		try {
			final ServerSocket s = new ServerSocket(8888);
			while (true) {
				final Socket s1 = s.accept();
				final OutputStream os = s1.getOutputStream();
				final DataOutputStream dos = new DataOutputStream(os);
				dos.writeUTF("Hello," + s1.getInetAddress() + "port#" + s1.getPort() + "  bye-bye!");
				dos.close();
				s1.close();
			}
		} catch (final IOException e) {
			e.printStackTrace();
			System.out.println("程序运行出错:" + e);
		}
	}
}