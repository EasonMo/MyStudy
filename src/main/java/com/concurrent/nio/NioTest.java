package com.concurrent.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class NioTest {

	public static void main(final String[] args) {

	}

	/**
	 * nio读写文件
	 */
	public static void nioCopyFile(final String resource, final String destination) throws IOException {
		final FileInputStream fis = new FileInputStream(resource);
		final FileOutputStream fos = new FileOutputStream(destination);
		final FileChannel readChannel = fis.getChannel(); // 读文件通道
		final FileChannel writeChannel = fos.getChannel(); // 写文件通道
		final ByteBuffer buffer = ByteBuffer.allocate(1024); // 读入数据缓存
		while (true) {
			buffer.clear();
			final int len = readChannel.read(buffer); // 读入数据
			if (len == -1) {
				break;
				// 读取完毕
			}
			buffer.flip();
			writeChannel.write(buffer);
			// 写入文件
		}
		readChannel.close();
		writeChannel.close();
	}

	/**
	 * 文件映射到内存
	 * 
	 * @throws IOException
	 */
	public void mapFile() throws IOException {
		final RandomAccessFile raf = new RandomAccessFile("C:\\mapfile.txt", "rw");
		final FileChannel fc = raf.getChannel();
		// 将文件映射到内存中
		final MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, 0, raf.length());
		while (mbb.hasRemaining()) {
			System.out.print((char) mbb.get());
		}
		mbb.put(0, (byte) 98); // 修改文件
		raf.close();
	}
}
