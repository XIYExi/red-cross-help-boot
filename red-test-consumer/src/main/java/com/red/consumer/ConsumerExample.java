package com.red.consumer;

import com.red.rpc.core.config.RpcConfig;
import com.red.rpc.core.utils.ConfigUtils;

public class ConsumerExample {
    public static void main(String[] args) {
        RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class, "rpc");


    }
}
