package com.red.rpc.core.serializer;

import java.io.IOException;

public interface Serializer {

    <T> byte[] serializer(T object) throws IOException;

    <T> T deserializer(byte[] bytes, Class<T> type) throws IOException;
}
