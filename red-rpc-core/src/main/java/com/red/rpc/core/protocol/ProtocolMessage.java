package com.red.rpc.core.protocol;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 协议消息结构
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProtocolMessage<T> {
    /**
     * 请求头
     */
    private Header header;

    /**
     * 请求体
     */
    private T body;

    /**
     * 请求头结构  17 字节
     */
    @Data
    public static class Header{
        /**
         * 魔数，保证安全性
         */
        private byte magic;

        /**
         * 版本号，保证一致性
         */
        private byte version;

        /**
         * 序列化器
         */
        private byte serializer;

        /**
         * 消息类型 （请求|响应）
         */
        private byte type;

        /**
         * 状态
         */
        private byte status;

        /**
         * 请求id 64bit
         */
        private long requestId;

        /**
         * 消息体长度 32bit
         */
        private int bodyLength;
    }
}
