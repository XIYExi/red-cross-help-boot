package com.red.rpc.core.registry;

import com.red.rpc.core.config.RegistryConfig;
import com.red.rpc.core.model.ServiceMetaInfo;
import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.kv.GetResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class EtcdRegistry implements Registry{

    private Client client;

    private KV kvClient;

    @Override
    public void init(RegistryConfig registryConfig) {

    }

    @Override
    public void registry(ServiceMetaInfo serviceMetaInfo) throws Exception {

    }

    @Override
    public void unRegistry(ServiceMetaInfo serviceMetaInfo) {

    }

    @Override
    public List<ServiceMetaInfo> serviceDiscovery(String serviceKey) {
        return null;
    }

    @Override
    public void destroy() {

    }
}
