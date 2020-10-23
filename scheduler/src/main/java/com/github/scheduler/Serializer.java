package com.github.scheduler;

import java.io.*;

/**
 * @author DongChan
 * @date 2020/10/23
 * @time 2:09 PM
 */
public interface Serializer {
    byte[] serialize(Object data);

    <T> T deserialize(Class<T> clazz, byte[] serializedData);

    Serializer DEFAULT_JAVA_SERIALIZER = new Serializer() {

        public byte[] serialize(Object data) {
            if (data == null)
                return null;
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
                 ObjectOutput out = new ObjectOutputStream(bos)) {
                out.writeObject(data);
                return bos.toByteArray();
            } catch (Exception e) {
                throw new RuntimeException("Failed to serialize object", e);
            }
        }

        public <T> T deserialize(Class<T> clazz, byte[] serializedData) {
            if (serializedData == null)
                return null;
            try (ByteArrayInputStream bis = new ByteArrayInputStream(serializedData);
                 ObjectInput in = new ObjectInputStream(bis)) {
                return clazz.cast(in.readObject());
            } catch (Exception e) {
                throw new RuntimeException("Failed to deserialize object", e);
            }
        }
    };
}
