package com.jvm.hotswap;

import java.lang.reflect.Method;

public class TestHotSwap {

	public static void main(final String[] args) throws Exception {
		// 开启线程，如果class文件有修改，就热替换
		final Thread t = new Thread(new MonitorHotSwap());
		t.start();
	}
}

class MonitorHotSwap implements Runnable {
	// Hot就是用于修改，用来测试热加载
	private final String className = "com.jvm.hotswap.Hot";
	private Class hotClazz = null;
	private HotSwapURLClassLoader hotSwapCL = null;

	@Override
	public void run() {
		try {
			while (true) {
				initLoad();
				final Object hot = hotClazz.newInstance();
				final Method m = hotClazz.getMethod("hot");
				m.invoke(hot, null); // 打印出相关信息
				// 每隔10秒重新加载一次
				Thread.sleep(10000);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加载class
	 */
	void initLoad() throws Exception {
		hotSwapCL = HotSwapURLClassLoader.getClassLoader();
		// 如果Hot类被修改了，那么会重新加载，hotClass也会返回新的
		hotClazz = hotSwapCL.loadClass(className);
	}
}