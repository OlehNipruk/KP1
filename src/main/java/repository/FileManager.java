package repository;

import java.io.*;
import java.util.List;

public class FileManager<T> {
    private String filePath;

    public FileManager(String filePath) {
        this.filePath = filePath;
    }

    public void saveObjects(List<T> objects) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            outputStream.writeObject(objects);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<T> loadObjects() {
        List<T> objects = null;
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath)) ) {
            objects = (List<T>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return objects;
    }
}