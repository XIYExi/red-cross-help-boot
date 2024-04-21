package com.red.rpc.core;

import cn.hutool.core.util.IdUtil;
import com.red.rpc.core.constant.RpcConstant;
import com.red.rpc.core.model.RpcRequest;
import com.red.rpc.core.model.RpcResponse;
import com.red.rpc.core.protocol.*;
import io.vertx.core.buffer.Buffer;
import org.junit.Assert;
import org.junit.Test;

public class ProtocolMessageTest {

    @Test
    public void testEncodeAndDecode() throws Exception {
        ProtocolMessage<RpcRequest> protocolMessage = new ProtocolMessage<>();
        ProtocolMessage.Header header = new ProtocolMessage.Header();
        header.setMagic(ProtocolConstant.PROTOCOL_MAGIC);
        header.setVersion(ProtocolConstant.PROTOCOL_VERSION);
        header.setSerializer((byte) ProtocolMessageSerializerEnum.JDK.getKey());
        header.setType((byte) ProtocolMessageTypeEnum.REQUEST.getKey());
        header.setStatus((byte) ProtocolMessageStatusEnum.OK.getValue());
        header.setRequestId(IdUtil.getSnowflakeNextId());
        header.setBodyLength(0);
        RpcRequest request = new RpcRequest();
        request.setServiceName("myService");
        request.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
        request.setMethodName("myMethod");
        request.setParameterTypes(new Class[]{String.class});
        request.setArgs(new Object[]{"aaa", "bbb"});
        protocolMessage.setHeader(header);
        protocolMessage.setBody(request);

        Buffer encodeBuffer = ProtocolMessageEncoder.encode(protocolMessage);
        ProtocolMessage<?> message = ProtocolMessageDecoder.decode(encodeBuffer);
        Assert.assertNotNull(message);

    }
}
