package com.red.rpc.core;

import com.red.rpc.core.fault.retry.FixedIntervalRetryStrategy;
import com.red.rpc.core.fault.retry.NoRetryStrategy;
import com.red.rpc.core.fault.retry.RetryStrategy;
import com.red.rpc.core.model.RpcResponse;
import org.junit.Test;

public class RetryStrategyTest {
    RetryStrategy retryStrategy = new FixedIntervalRetryStrategy();

    @Test
    public void doRetry() {
        try {
            RpcResponse rpcResponse = retryStrategy.doRetry(() -> {
                System.out.println("测试重试");
                throw new RuntimeException("模拟重试失败");
            });
            System.out.println(rpcResponse);
        }catch (Exception e) {
            System.out.println("重试多次失败");
            e.printStackTrace();
        }
    }
}
