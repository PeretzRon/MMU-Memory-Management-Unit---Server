package com.hit.services;

import com.hit.dao.IDao;
import com.hit.dm.DataModel;
import com.hit.memory.CacheUnit;

public class CacheUnitService<T> {

	private CacheUnit<T> cacheUnit;
	private IDao<Long, DataModel<T>> iDao;
	private int pageFaults;
	private int countRequests;
	private int countDataModelsReq;
	private int swaps;
	private boolean alreadyCount, getSuccess;

	public CacheUnitService(CacheUnit<T> cacheUnit, IDao<Long, DataModel<T>> iDao) {
		super();
		this.cacheUnit = cacheUnit;
		this.iDao = iDao;
		this.pageFaults = 0;
		this.countRequests = 0;
		this.countDataModelsReq = 0;
		this.swaps = 0;
		this.alreadyCount = false;
		this.getSuccess = true;
	}

	@SuppressWarnings("unchecked")
	public boolean update(DataModel<T>[] dataModels) {
		try {
			if (alreadyCount == false) {
				countDataModelsReq += dataModels.length;
				countRequests++;
			}
			DataModel<T>[] data = new DataModel[dataModels.length];
			data = cacheUnit.putDataModels(dataModels);

			for (DataModel<T> elem : data) {
				if (elem != null) {
					swaps++;
					iDao.save(elem);
				}
			}

		} catch (Exception e) {
			return false;
		}
		return true;

	}

	public boolean delete(DataModel<T>[] dataModels) {
		try {
			countDataModelsReq += dataModels.length;
			countRequests++;
			Long id[] = new Long[dataModels.length];
			int i = 0;
			for (DataModel<T> dataModel : dataModels) {
				id[i++] = dataModel.getDataModelId();
				iDao.delete((DataModel<T>) dataModel);
			}
			cacheUnit.removeDataModels(id);
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}

	}

	@SuppressWarnings("unchecked")
	public DataModel<T>[] get(DataModel<T>[] dataModels) {
		try {
			getSuccess = true;
			countDataModelsReq += dataModels.length;
			countRequests++;
			alreadyCount = true;
			DataModel<T>[] data = new DataModel[dataModels.length];
			Long id[] = new Long[dataModels.length];
			int i = 0;
			for (DataModel<T> dataModel : dataModels) {
				id[i++] = dataModel.getDataModelId();
			}

			data = cacheUnit.getDataModels(id);
			for (i = 0; i < data.length; i++) {
				if (data[i] == null) {
					pageFaults++;
					data[i] = (DataModel<T>) iDao.find(dataModels[i].getDataModelId());
				}
			}

			this.update(data);
			alreadyCount = false;
			return data;
		}

		catch (IllegalArgumentException e) {
			getSuccess = false;
			System.out.println(e);
		}

		catch (Exception e) {
			getSuccess = false;
			e.printStackTrace();
		}
		return dataModels;

	}

	

	public String getStatistics() {

		String output = "";
		final String capacity = "Capacity: " + "3" + "\n";
		final String algorithm = "Algorithm: " + this.getAlgoName() + "\n";
		final String requsets = "Total number of requests: " + countRequests + "\n";
		final String requsetsDataModels = "Total number of DataModels (GET/DELETE/UPDATE): " + countDataModelsReq
				+ "\n";
		final String numberOfSwaps = "Total number of DataModel swaps (from Cache to Disk): " + swaps + "\n";
		output += capacity + algorithm + requsets + requsetsDataModels + numberOfSwaps;
		return output;
	}

	private String getAlgoName() {
		String algoName = "";
		String getAlgoClassName = cacheUnit.getAlgo().getClass().toString();
		if (getAlgoClassName.contains("LRU")) {
			algoName = "LRU";
		} else if (getAlgoClassName.contains("Random")) {
			algoName = "Random";
		} else if (getAlgoClassName.contains("NRU")) {
			algoName = "NRU";
		}
		return algoName;
	}
	
	public boolean isGetSuccess() {
		return getSuccess;
	}

	public CacheUnit<T> getCacheUnit() { // getter for print
		return cacheUnit;
	}


}
