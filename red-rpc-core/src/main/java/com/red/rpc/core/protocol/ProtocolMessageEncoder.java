package com.red.rpc.core.protocol;


import com.red.rpc.core.serializer.Serializer;
import com.red.rpc.core.serializer.SerializerFactory;
import io.vertx.core.buffer.Buffer;

/**
 * 协议编码器
 */
public class ProtocolMessageEncoder {


    public static Buffer encode(ProtocolMessage<?> protocolMessage) throws Exception {
        if (protocolMessage == null || protocolMessage.getHeader() == null) {
            return Buffer.buffer();
        }

        ProtocolMessage.Header header = protocolMessage.getHeader();
        // 依次向缓冲区写入字节
        Buffer buffer = Buffer.buffer();
        buffer.appendByte(header.getMagic());
        buffer.appendByte(header.getVersion());
        buffer.appendByte(header.getSerializer());
        buffer.appendByte(header.getType());
        buffer.appendByte(header.getStatus());
        buffer.appendLong(header.getRequestId());
        // 获取序列化器
        ProtocolMessageSerializerEnum serializerEnum = ProtocolMessageSerializerEnum.getEnumByKey(header.getSerializer());

        if (serializerEnum == null)
            throw new RuntimeException("序列化协议不存在");

        Serializer serializer = SerializerFactory.getInstance(serializerEnum.getValue());
        byte[] bodyBytes = serializer.serializer(protocolMessage.getBody());
        // 写入body长度和数据
        buffer.appendInt(bodyBytes.length);
        buffer.appendBytes(bodyBytes);
        return buffer;
    }

}
