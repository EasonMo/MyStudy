package com.bjsxt.socket;

/*	范例名称：简单的client/server程序
 * 	源文件名称：TestClient.java/TestServer.java
 *	要  点：
 *		1. Java Socket编程步骤
 *		2. Socket/ServerSocket类用法
 *		3. 通过Socket对象可以获取通信对方Socket的信息
 */

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.Socket;

public class TestClient {
	public static void main(final String args[]) {
		try {
			final Socket s1 = new Socket("127.0.0.1", 8888);
			final InputStream is = s1.getInputStream();
			final DataInputStream dis = new DataInputStream(is);
			System.out.println(dis.readUTF());
			dis.close();
			s1.close();
		} catch (final ConnectException connExc) {
			connExc.printStackTrace();
			System.err.println("服务器连接失败！");
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
}