package com.red.rpc.core.serializer;

import com.red.rpc.core.spi.SpiLoader;

import java.util.HashMap;
import java.util.Map;

public class SerializerFactory {

   static {
       SpiLoader.load(Serializer.class);
   }

    /**
     * 默认序列化器
     */
   private static final Serializer DEFAULT_SERIALIZER = new JdkSerializer();


    /**
     * 获取实例
     * @param key
     * @return
     */
    public static Serializer getInstance(String key) {
        return SpiLoader.getInstance(Serializer.class, key);
    }

}
