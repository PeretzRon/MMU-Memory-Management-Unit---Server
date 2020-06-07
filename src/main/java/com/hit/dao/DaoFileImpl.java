package com.hit.dao;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import com.hit.dm.DataModel;

public class DaoFileImpl<T> implements IDao<Long, DataModel<T>> {

    private String filePath;
    private int capacity;
    private int countDataModels;
    private Map<Long, DataModel<T>> dataMap;

    public DaoFileImpl(String filePath, int capacity) { // C'tor with capacity file
        super();
        this.filePath = filePath;
        this.capacity = capacity;
        this.countDataModels = 0;
        this.dataMap = new HashMap<>();
    }

    public DaoFileImpl(String filePath) { // C'tor with out capacity file
        super();
        this.filePath = filePath;
        this.capacity = Integer.MAX_VALUE;
        this.countDataModels = 0;
        this.dataMap = new HashMap<>();
    }

    //delete function delete the entity fro, the map and than save again all the map in the file. (delete == null)
    //not really delete
    @Override
    public void delete(DataModel<T> entity) throws IOException {
        if (entity == null)
            throw new IllegalArgumentException("entity can't be null!!");

        Long id = entity.getDataModelId();
        if (dataMap.containsKey(id)) {
            entity.setContent(null);
        }
        countDataModels--;
        save(entity);
    }

    //find function get the data model from the map (not from the file) and return that.
    // if id is null --> exception
    @Override
    public DataModel<T> find(Long id) {

        if (id == null) {
            throw new IllegalArgumentException("ID can't be null!!");
        }
        if (dataMap.containsKey(id)) {
            return dataMap.get(id);
        }
        return null;
    }

    // save function get one data model and save to file "DataSource.txt"
    @Override
    public void save(DataModel<T> entity) throws IOException {
        if (countDataModels < capacity) {

            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath));
            Long id = entity.getDataModelId();

            if (!dataMap.containsKey(id)) {
                countDataModels++;
            }
            dataMap.put(entity.getDataModelId(), entity);

            out.writeObject(dataMap);
            out.flush();
            out.close();

        }

    }

}
