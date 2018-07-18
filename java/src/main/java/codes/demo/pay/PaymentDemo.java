package codes.demo.pay;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class PaymentDemo {

	private static final int DEFAULT_WAIT_TIME = 1 * 1000;
	
	private PaymentRemoteSerivce paymentRemoteSerivce;

	private int waitTime;
	
	public PaymentDemo(PaymentRemoteSerivce paymentRemoteSerivce) {
		this.paymentRemoteSerivce = paymentRemoteSerivce;
		this.waitTime = DEFAULT_WAIT_TIME;
	}
	
	public PaymentDemo(PaymentRemoteSerivce paymentRemoteSerivce, int waitTime) {
		this.paymentRemoteSerivce = paymentRemoteSerivce;
		this.waitTime = waitTime;
	}
	
	/**
	 * @param paymentTypes 支付方式列表
	 * @return 支付方式和支付方式是否可用Map
	 */
	public Map<String, ConsultResult> getPaymentInfos(List<String> paymentTypes) {
		
		/* 初始化默认返回值 */
		ConsultResult defaultResult = new ConsultResult(false, "timeout-code");
		Map<String, ConsultResult> result = paymentTypes.stream().collect(
				Collectors.toMap(paymentType -> paymentType, paymentType -> defaultResult));

		ExecutorService executorService = Executors.newFixedThreadPool(paymentTypes.size(), 
				r -> {Thread t = new Thread(r);t.setDaemon(true);return t;});
		CountDownLatch latch = new CountDownLatch(paymentTypes.size());

		for (String paymentType : paymentTypes) {
			executorService.submit(() -> {
				result.put(paymentType, paymentRemoteSerivce.isEnabled(paymentType));
				latch.countDown();
			});
		}
		
		try {
			latch.await(waitTime, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
			Thread.interrupted();
		}

		return result;
	}

	/**
	 * 场景模拟
	 * 最短响应时间获得尽可能多的可用支付方式列表
	 */
	public static void main(String[] args) {

		List<String> paymentTypes = new ArrayList<>();
		paymentTypes.add("balance");
		paymentTypes.add("red-packet");
		paymentTypes.add("coupon");
		paymentTypes.add("cash-coupon");
		System.out.println("支付方式列表：" + paymentTypes);
		
		Map<String, ConsultResult> result = 
				/* Mock一个 PaymentRemoteSerivce */
				new PaymentDemo(paymentType -> new ConsultResult(true, null))
					/* 获取可用支付方式列表 */
					.getPaymentInfos(paymentTypes);
		
		System.out.println("可用支付方式列表如下：");
		for (Map.Entry<String, ConsultResult> entry : result.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue().getIsEnable() + " " + entry.getValue().getErrorCode());
		}
		
	}
	
}
