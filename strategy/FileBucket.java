package com.javarush.task.task33.task3310.strategy;

import com.javarush.task.task33.task3310.ExceptionHandler;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileBucket {
    Path path;

    public FileBucket()  {

        try {
            path = Files.createTempFile("tmp", null);
            Files.deleteIfExists(path);
           Files.createFile(path);
        } catch (IOException e) {
           ExceptionHandler.log(e);
        }finally {
            path.toFile().deleteOnExit();
        }
        }

    public long getFileSize() {
        try {
            return Files.size(path);
        } catch (IOException e) {
            ExceptionHandler.log(e);
        }
        return Long.parseLong(null);
    }

    public void putEntry(Entry entry) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(path));
            for (;entry != null; entry = entry.next){
                objectOutputStream.writeObject(entry);
            }
            objectOutputStream.close();
        } catch (IOException e) {
            ExceptionHandler.log(e);
        }
    }
    public Entry getEntry()  {
        if (getFileSize() == 0){
            return null;
        }
        Entry entry = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(path));
            Object o = objectInputStream.readObject();

            objectInputStream.close();
             entry = (Entry) o;
        } catch (IOException | ClassNotFoundException e) {
            ExceptionHandler.log(e);
        }
        return entry;
    }
    public void remove(){
        try {
            Files.delete(path);
        } catch (IOException e) {
            ExceptionHandler.log(e);
        }
    }

}
