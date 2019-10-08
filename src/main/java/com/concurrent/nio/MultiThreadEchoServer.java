package com.concurrent.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 常规的socket服务端，服务器端采用一个线程接受一个客户端来处理。 Created by chenyang on 2017/3/26.
 */
public class MultiThreadEchoServer {
	static class HandleMsg implements Runnable {
		Socket clientSocket;

		public HandleMsg(final Socket clientSocket) {
			this.clientSocket = clientSocket;
		}

		@Override
		public void run() {
			BufferedReader is = null;
			PrintWriter os = null;
			try {
				is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				os = new PrintWriter(clientSocket.getOutputStream(), true);
				// 从InputStream当中读取客户端所发送的数据
				String inputLine = null;
				final long b = System.currentTimeMillis();
				while ((inputLine = is.readLine()) != null) {
					os.println(inputLine);
				}
				final long e = System.currentTimeMillis();
				System.out.println("spend:" + (e - b) + "ms");
			} catch (final IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (is != null) {
						is.close();
					}
					if (os != null) {
						os.close();
					}
					clientSocket.close();
				} catch (final IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	private static ExecutorService tp = Executors.newCachedThreadPool();

	public static void main(final String[] args) {
		ServerSocket echoServer = null;
		Socket clientSocket = null;
		try {
			echoServer = new ServerSocket(8000);
		} catch (final IOException e) {
			System.out.println(e);
		}
		while (true) {
			try {
				clientSocket = echoServer.accept();// 阻塞
				System.out.println(clientSocket.getRemoteSocketAddress() + " connect!" + System.currentTimeMillis());

				// 子线程负责执行与client socket 交互的操作。
				tp.execute(new HandleMsg(clientSocket));
			} catch (final IOException e) {
				System.out.println(e);
			}
		}
	}
}
