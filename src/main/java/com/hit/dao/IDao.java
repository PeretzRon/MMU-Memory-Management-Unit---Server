package com.hit.dao;

import java.io.IOException;

// IDao interface for DB or file, in our case is for file
public interface IDao<ID extends java.io.Serializable, T> {

    public void delete(T entity) throws IOException;

    public T find(ID id);

    public void save(T entity) throws IOException;

}
