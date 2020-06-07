package com.hit.services;

import java.io.IOException;

import com.hit.algorithm.IAlgoCache;
import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.dao.DaoFileImpl;
import com.hit.dao.IDao;
import com.hit.dm.DataModel;
import com.hit.memory.CacheUnit;

public class CacheUnitController<T> {

	private CacheUnitService<T> cacheUnitService;

	public CacheUnitController() {
		super();
		IDao<Long, DataModel<T>> iDao = new DaoFileImpl<>("src\\main\\resources\\DataSource.txt");
		IAlgoCache<Long, DataModel<T>> iLRUAlgo = new LRUAlgoCacheImpl<>(3);
		CacheUnit<T> cacheUnit = new CacheUnit<T>(iLRUAlgo);
		this.cacheUnitService = new CacheUnitService<>(cacheUnit, iDao);
	}

	public boolean update(DataModel<T>[] dataModels) throws IOException {
		return cacheUnitService.update(dataModels);

	}

	public boolean delete(DataModel<T>[] dataModels) throws IOException {
		return cacheUnitService.delete(dataModels);
	}

	public DataModel<T>[] get(DataModel<T>[] dataModels) {
		return cacheUnitService.get(dataModels);

	}
	

	public CacheUnitService<T> getCacheUnitService() { // getter for print
		return cacheUnitService;
	}


}
