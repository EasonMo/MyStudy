package com.concurrent.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chenyang on 2017/4/8.
 */
public class MultiThreadNIOEchoServer {

	// 保存需要回复客户端的信息
	class EchoClient {
		private final LinkedList<ByteBuffer> outq;

		EchoClient() {
			outq = new LinkedList<ByteBuffer>();
		}

		// enqueue a ByteBuffer on the output queue.
		public void enqueue(final ByteBuffer bb) {
			outq.addFirst(bb);
		}

		// return the output queue
		public LinkedList<ByteBuffer> getOutputQueue() {
			return outq;
		}
	}

	class HandleMsg implements Runnable {
		SelectionKey sk;
		ByteBuffer bb;

		public HandleMsg(final SelectionKey sk, final ByteBuffer bb) {
			this.sk = sk;
			this.bb = bb;
		}

		@Override
		public void run() {
			final EchoClient echoClient = (EchoClient) sk.attachment();
			echoClient.enqueue(bb);

			// we've enqueued data to be written to the client,we must
			// not set interest in OP_WRITE
			sk.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
			selector.wakeup();
		}
	}

	// 用来统计每个连接的时间
	public static Map<Socket, Long> geym_time_stat = new HashMap<Socket, Long>(10240);

	public static void main(final String[] args) {
		final MultiThreadNIOEchoServer echoServer = new MultiThreadNIOEchoServer();
		try {
			echoServer.startServer();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private Selector selector;

	private final ExecutorService tp = Executors.newCachedThreadPool();

	private void disconnect(final SelectionKey sk) {
		final SocketChannel sc = (SocketChannel) sk.channel();
		try {
			sc.finishConnect();
		} catch (final IOException e) {

		}
	}

	/*
	 * 与客户端建立连接 accept a new client and set it up for reading
	 */
	private void doAccept(final SelectionKey sk) {
		// 取出服务器的channel
		final ServerSocketChannel server = (ServerSocketChannel) sk.channel();
		SocketChannel clientChannel;
		try {
			// 获取客户端的channel
			clientChannel = server.accept();
			clientChannel.configureBlocking(false);

			// 把客户端channel注册到selector上，并对读操作感兴趣
			final SelectionKey clientKey = clientChannel.register(selector, SelectionKey.OP_READ);
			// Allocate an EchoClient instance and attach it to this selection
			// key.
			final EchoClient echoClient = new EchoClient();
			clientKey.attach(echoClient);

			final InetAddress clientAddress = clientChannel.socket().getInetAddress();
			System.out.println("Accepted connetion from " + clientAddress.getHostAddress() + ".");
		} catch (final Exception e) {
			System.out.println("Failed to accept new client");
			e.printStackTrace();
		}
	}

	private void doRead(final SelectionKey sk) {
		final SocketChannel channel = (SocketChannel) sk.channel();
		final ByteBuffer bb = ByteBuffer.allocate(8192);
		int len;

		try {
			// 读取数据到byteBuffer
			len = channel.read(bb);
			if (len < 0) {
				disconnect(sk);
				return;
			}
		} catch (final Exception e) {
			System.out.println("Fail to read from client");
			e.printStackTrace();
			disconnect(sk);
			return;
		}
		bb.flip();
		tp.execute(new HandleMsg(sk, bb));
	}

	private void doWrite(final SelectionKey sk) {
		final SocketChannel channel = (SocketChannel) sk.channel();
		final EchoClient echoClient = (EchoClient) sk.attachment();
		final LinkedList<ByteBuffer> outq = echoClient.getOutputQueue();

		final ByteBuffer bb = outq.getLast();
		try {
			final int len = channel.write(bb);
			if (len == -1) {
				disconnect(sk);
				return;
			}
			if (bb.remaining() == 0) {
				outq.removeLast();
			}
		} catch (final Exception e) {
			e.printStackTrace();
			System.out.println("fail to write to client");
			disconnect(sk);
		}

		if (outq.size() == 0) {
			sk.interestOps(SelectionKey.OP_READ);
		}

	}

	private void startServer() throws Exception {
		// 声明一个selector
		selector = SelectorProvider.provider().openSelector();

		// 声明一个server socket channel,而且是非阻塞的。
		final ServerSocketChannel ssc = ServerSocketChannel.open();
		ssc.configureBlocking(false);

		// InetSocketAddress isa=new
		// InetSocketAddress(InetAddress.getLocalHost(),8000);
		// 声明服务器端的端口
		final InetSocketAddress isa = new InetSocketAddress(8000);
		// 服务器端的socket channel绑定在这个端口。
		ssc.socket().bind(isa);
		// 把一个ServerSocketChannel注册到一个selector上，同时选择监听的事件，
		// SelectionKey.OP_ACCEPT表示如果selector监听到注册在它上面的ServerSocketChannel准备去接受一个连接，
		// 或有个错误挂起，selector将把OP_ACCEPT加到keyReadySet，并把key加到selectedKeySet
		final SelectionKey acceptKey = ssc.register(selector, SelectionKey.OP_ACCEPT);

		for (;;) {
			selector.select();
			final Set readyKeys = selector.selectedKeys();
			final Iterator i = readyKeys.iterator();
			long e = 0;
			while (i.hasNext()) {
				final SelectionKey sk = (SelectionKey) i.next();
				i.remove();
				// 接受状态
				if (sk.isAcceptable()) {
					doAccept(sk);
				}
				// 可读状态
				else if (sk.isValid() && sk.isReadable()) {
					if (!geym_time_stat.containsKey(((SocketChannel) sk.channel()).socket())) {
						geym_time_stat.put(((SocketChannel) sk.channel()).socket(), System.currentTimeMillis());
						doRead(sk);
					}
				}
				// 可写状态
				else if (sk.isValid() && sk.isWritable()) {
					doWrite(sk);
					e = System.currentTimeMillis();
					final long b = geym_time_stat.remove(((SocketChannel) sk.channel()).socket());
					System.out.println("spend" + (e - b) + "ms");
				}
			}
		}
	}
}
