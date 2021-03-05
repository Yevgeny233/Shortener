package com.javarush.task.task33.task3310;

import com.javarush.task.task33.task3310.strategy.StorageStrategy;

public class Shortener {
    private Long lastId = 0l; //Это поле будет

    // отвечать за последнее значение идентификатора, которое было
    // использовано при добавлении новой строки в хранилище.

    private StorageStrategy storageStrategy; //в котором будет храниться стратегия хранения данных.

    public Shortener(StorageStrategy storageStrategy) {
        this.storageStrategy = storageStrategy;
    }


    public synchronized Long getId(String string){
      if (storageStrategy.containsValue(string)) {
          return storageStrategy.getKey(string);
      }else {
          lastId++;
          storageStrategy.put(lastId, string);

        return lastId;
    }
    }
    public synchronized String getString(Long id){

        return storageStrategy.getValue(id);
    }
}
