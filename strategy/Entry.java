package com.javarush.task.task33.task3310.strategy;

import java.io.Serializable;
import java.util.Objects;

public class Entry implements Serializable {
     Long key;
     String value;
     Entry next;
     int hash;

    public Entry(int hash, Long key, String value, Entry next) {
        this.key = key;
        this.value = value;
        this.next = next;
        this.hash = hash;
    }

    public Long getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public final boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Entry entry = (Entry) o;
        return key.equals(entry.key) && value.equals(entry.value);
    }
    @Override
    public int hashCode() {
        return Objects.hash(key,value);
    }

    @Override
    public String toString() {
        return key + "=" + value;
    }
}
