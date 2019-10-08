package com.pattern.future;

public class FutureTest {
	private interface Data {
		public String getResult();
	}

	private class FutureData implements Data {
		protected RealData realData = null;
		protected boolean isReady = false;

		@Override
		public synchronized String getResult() {
			while (!isReady) {
				try {
					wait();// 一直等待，直到realData被注入
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
			return realData.result;
		}

		public synchronized void setRealData(final RealData realData) {
			if (isReady) {
				return;
			}
			this.realData = realData;
			isReady = true;
			notifyAll();// realData已经被注入，通知getResult()
		}
	}

	private class RealData implements Data {
		protected final String result;

		public RealData(final String para) {
			// realData的构造可能很慢，需要用户等待很久，用sleep模拟
			final StringBuffer sb = new StringBuffer();
			for (int i = 0; i < 10; i++) {
				sb.append(para);
				try {
					Thread.sleep(100);
				} catch (final InterruptedException e) {
					e.printStackTrace();
				}
			}
			result = sb.toString();
		}

		@Override
		public String getResult() {
			return result;
		}
	}

	public static void main(final String[] args) {
		final FutureTest client = new FutureTest();
		// 这里会立即返回，因为得到的是FutureData而不是RealData
		final Data data = client.request("name");
		System.out.println("请求完毕");
		try {
			// 这里可以用一个sleep代替对其他业务逻辑的处理
			// 在处理这些业务逻辑的过程中，RealData被创建，从而充分利用了等待时间
			Thread.sleep(1000);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		// 使用真实数据
		System.out.println("数据：" + data.getResult());
	}

	public Data request(final String queryStr) {
		final FutureData future = new FutureData();
		new Thread() {
			@Override
			public void run() {
				final RealData realData = new RealData(queryStr);
				future.setRealData(realData);
			}

		}.start();
		return future;
	}

}
