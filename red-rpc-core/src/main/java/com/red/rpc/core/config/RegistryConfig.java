package com.red.rpc.core.config;

import com.red.rpc.core.registry.RegistryKeys;
import lombok.Data;

@Data
public class RegistryConfig {

    private String registry = RegistryKeys.ETCD;

    private String address = "http://localhost:2380";

    private String username;

    private String password;

    private Long timeout = 10000L;

}
