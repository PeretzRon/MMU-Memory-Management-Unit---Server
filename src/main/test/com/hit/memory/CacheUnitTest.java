package com.hit.memory;
import com.hit.algorithm.IAlgoCache;
import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.dao.DaoFileImpl;
import com.hit.dao.IDao;
import com.hit.dm.DataModel;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class CacheUnitTest {

    private String expected, unexpected, actual;
    private int cacheCapacity = 3, dataModels_Size = 10;
    private IDao<Long, DataModel<String>> iDao;
    private IAlgoCache<Long, DataModel<String>> iLRUAlgo;
    private DataModel<String>[] dataModels;
    private CacheUnit<String> cacheUnit;

    Long id[] = { (long) 1, (long) 2, (long) 3, (long) 4, (long) 5, (long) 6, (long) 7, (long) 8, (long) 9, (long) 10 };
    String content[] = { "Ron", "Meital", "Ayelet", "Dor", "Yulia", "Hana", "Ortal", "Alon", "Dvir", "Gil" };

    @SuppressWarnings("unchecked")
    @Test
    void test() {
        try {
            iDao = new DaoFileImpl<>("src\\main\\resources\\DataSource.txt");
            iLRUAlgo = new LRUAlgoCacheImpl<>(cacheCapacity);
            dataModels = new DataModel[dataModels_Size];
            cacheUnit = new CacheUnit<String>(iLRUAlgo);

            for (int i = 0; i < dataModels_Size; ++i) {
                dataModels[i] = new DataModel<String>(id[i], content[i]);
                iDao.save(dataModels[i]); // create the file with all dataModels
            }

            DataModel<String>[] temp = new DataModel[dataModels_Size];
            temp = cacheUnit.putDataModels(dataModels);

            for (int i = 0; i < 7; i++) {
                Assertions.assertEquals(dataModels[i].getContent(), temp[i].getContent());
            }

            Long id[] = { (long) 9 };
            cacheUnit.removeDataModels(id);

            Long id2[] = { (long) 1, (long) 10, (long) 9 };
            DataModel<String>[] temp2 = new DataModel[cacheCapacity];
            temp2 = cacheUnit.getDataModels(id2);

            Assertions.assertEquals(null, temp2[0]);
            Assertions.assertEquals("Gil", temp2[1].getContent());
            Assertions.assertEquals(null, temp2[2]);

            Assertions.assertEquals("Ron", iDao.find((long) 1).getContent());
            Assertions.assertEquals("Dvir", iDao.find((long) 9).getContent());

            iLRUAlgo.putElement((long) 1, dataModels[0]);
            iLRUAlgo.putElement((long) 9, dataModels[8]);

            Long id3[] = { (long) 1, (long) 10 };
            cacheUnit.removeDataModels(id3);

            DataModel<String>[] temp3 = new DataModel[2];
            temp3 = cacheUnit.getDataModels(id3);
            Assertions.assertEquals(null, temp3[0]);
            Assertions.assertEquals(null, temp3[1]);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
