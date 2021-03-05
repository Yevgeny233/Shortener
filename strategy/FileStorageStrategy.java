package com.javarush.task.task33.task3310.strategy;

public class FileStorageStrategy implements StorageStrategy{
    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final long DEFAULT_BUCKET_SIZE_LIMIT = 10000;
    FileBucket[] table = new FileBucket[DEFAULT_INITIAL_CAPACITY];
    int size;
    private long bucketSizeLimit = DEFAULT_BUCKET_SIZE_LIMIT;
    long maxBucketSize;

    public long getBucketSizeLimit() {
        return bucketSizeLimit;
    }

    public void setBucketSizeLimit(long bucketSizeLimit) {
        this.bucketSizeLimit = bucketSizeLimit;
    }

    final int hash(Long key) {
        return key.hashCode();
    }

    static int indexFor(int hash, int length) {
        return hash &(length - 1);
    }
    final Entry getEntry(Long key) {
        if (size == 0){
            return null;
        }
        int hash = hash(key);
        int index = indexFor(hash, table.length);
        for (Entry entry = table[index].getEntry(); entry != null; entry = entry.next){
            if (key.equals(entry.key)){
                return entry;
            }
        }
        return null;
    }
    @Override
    public void put(Long key, String value) {
        int hash = hash(key);
        int index = indexFor(hash,table.length);
        if (table[index] == null){
            createFileBucket(hash,key,value,index);
        }else {
            for (Entry entry = table[index].getEntry(); entry != null; entry = entry.next) {
                if (key.equals(entry.key)) {
                    entry.value = value;
                }
            }
            addEntry(hash, key, value, index);
        }
    }

    void addEntry(int hash, Long key, String value, int bucketIndex) {
        if ((size >= getBucketSizeLimit())) {
            resize(2 * table.length);
            hash = hash(key);
            bucketIndex = indexFor(hash, table.length);
        }

        createFileBucket(hash, key, value, bucketIndex);
    }
    void resize(int newCapacity) {
        FileBucket[] entries = new FileBucket[newCapacity];
        transfer(entries);
        table = entries;
        setBucketSizeLimit( (int)(newCapacity * maxBucketSize));
        table[(int) bucketSizeLimit].remove();
    }

    void transfer(FileBucket[] newTable) {
        int newcapacity = newTable.length;
        for (FileBucket f : table){
            while (f != null){
                FileBucket entrynext = f;
                int newindex = indexFor(hash(f.getEntry().key), newcapacity);
                for (int i = 1; i < table.length; i++){
                    for (int j = 0; j < i; j++){
                        table[j] = newTable[newindex];
                        table[newindex] = table[i];
                        table[i] = table[j];
                    }
                }
            }
        }
    }
    void createFileBucket(int hash, Long key, String value, int bucketIndex) {
       table[bucketIndex] = new FileBucket();
        table[bucketIndex].putEntry(new Entry(hash, key, value, null));
        size++;
    }


    @Override
    public boolean containsKey(Long key) {
        return getEntry(key) != null;
    }

    @Override
    public boolean containsValue(String value) {
        if (table == null) {
            return false;
        }
        return getKey(value) != null ;
    }


    @Override
    public Long getKey(String value) {
        for (FileBucket tableElement : table) {
            if (tableElement == null){
                continue;
            }
            for (Entry e = tableElement.getEntry(); e != null; e = e.next) {
                if (value.equals(e.value)) {
                    return e.getKey();
                }
            }
        }
        return null;
    }

    @Override
    public String getValue(Long key) {
        Entry e = getEntry(key);
        if (e != null){
            return e.getValue();
        }
        return  null;
    }
}
