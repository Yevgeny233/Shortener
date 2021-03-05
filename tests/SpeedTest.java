package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Helper;
import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.HashBiMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.HashMapStorageStrategy;
import org.junit.Assert;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SpeedTest {
    public long getTimeToGetIds(Shortener shortener, Set<String> strings, Set<Long> ids){
       Date datastart = new Date();
        for(String s : strings){
            ids.add(shortener.getId(s));
        }
        Date datafinish = new Date();
        return datafinish.getTime() - datastart.getTime();

    }
    public long getTimeToGetStrings(Shortener shortener, Set<Long> ids, Set<String> strings){
        Date datastart = new Date();
        for(Long id : ids){
            strings.add(shortener.getString(id));
        }
        Date datafinish = new Date();
        return datafinish.getTime() - datastart.getTime();
    }
    public void testHashMapStorage(){
        Shortener shortener1 = new Shortener(new HashMapStorageStrategy());
        Shortener shortener2 = new Shortener(new HashBiMapStorageStrategy());
        Set <String> origStrings = new HashSet<>();

        for (int i = 0; i < 1000; i++){
            origStrings.add(Helper.generateRandomString());

        }
        Set<Long> longsshortener1 = new HashSet<>();
        Set<Long> longsshortener2 = new HashSet<>();
        for (String s : origStrings){
            longsshortener1.add(shortener1.getId(s));
            longsshortener2.add(shortener2.getId(s));
        }

       Long timegeIds1 = getTimeToGetIds(shortener1,origStrings,longsshortener1);
        Long timegeIds2 = getTimeToGetIds(shortener2,origStrings,longsshortener2);
        Assert.assertNotEquals(timegeIds1,timegeIds2);

        Long time1 = getTimeToGetStrings(shortener1,longsshortener1, origStrings);
        Long time2 = getTimeToGetStrings(shortener2,longsshortener2, origStrings);
        Assert.assertEquals(time1,time2, 30);
    }
}
