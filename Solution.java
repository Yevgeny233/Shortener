package com.javarush.task.task33.task3310;

import com.javarush.task.task33.task3310.strategy.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Solution {
    public static Set<Long> getIds(Shortener shortener, Set<String> strings){
        Set<Long> set = new HashSet<>();
        for (String s : strings){
           set.add(shortener.getId(s));
        }
        return set;
    }
    public static Set<String> getStrings(Shortener shortener, Set<Long> keys){
        Set<String> stringSet = new HashSet<>();
        for (Long l : keys){
            stringSet.add(shortener.getString(l));
        }
        return stringSet;
    }
    public static void testStrategy(StorageStrategy strategy, long elementsNumber){
        Helper.printMessage(strategy.getClass().getSimpleName());

        Set <String> stringSet = new HashSet<String>();

        for (int i = 0; i < elementsNumber; i++){
            stringSet.add(Helper.generateRandomString());
        }
        Shortener shortener = new Shortener(strategy);
        Date start = new Date();
        Set<Long> set = getIds(shortener, stringSet);
        Date finish = new Date();
        Date start1 = new Date();
        Set<String> strings = getStrings(shortener, set);
       Date finish1 = new Date();
        Helper.printMessage("Time work getIds "+String.valueOf(finish.getTime() - start.getTime()));
        Helper.printMessage("Time work getStrings "+String.valueOf(finish1.getTime() - start1.getTime()));
        if (strings.size() == stringSet.size()){
            Helper.printMessage("Тест пройден.");
        }else {
            Helper.printMessage("Тест не пройден.");
        }


    }
    public static void main(String[] args) {
        testStrategy(new HashMapStorageStrategy(), 10000 );
        testStrategy(new OurHashMapStorageStrategy(), 10000 );
       testStrategy(new FileStorageStrategy(), 50);
        testStrategy(new OurHashBiMapStorageStrategy(), 10000);
        testStrategy(new HashBiMapStorageStrategy(), 10000);
        testStrategy(new DualHashBidiMapStorageStrategy(), 10000);
    }

}
