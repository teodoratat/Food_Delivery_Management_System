package com.teo.cateringteo.DataAccess;
import java.io.*;

public class Serializer<T> {
    public void serialize(T genericObject, String filePath) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(genericObject);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public T deserialize(String filePath) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        T genericObject = (T) objectInputStream.readObject();
        objectInputStream.close();
        return genericObject;
    }
}
