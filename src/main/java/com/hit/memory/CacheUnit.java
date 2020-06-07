package com.hit.memory;

import com.hit.algorithm.IAlgoCache;
import com.hit.dm.DataModel;

public class CacheUnit<T> {

    private com.hit.algorithm.IAlgoCache<Long, DataModel<T>> algo;

    public CacheUnit(IAlgoCache<Long, DataModel<T>> algo) {
        super();
        this.algo = algo;
    }

    @SuppressWarnings("unchecked")
    public DataModel<T>[] getDataModels(Long[] ids) {

        DataModel<T>[] dataModels = new DataModel[ids.length];
        DataModel<T> dataModel = new DataModel<>();
        int i = 0;

        for (Long id : ids) {
            dataModel = algo.getElement(id);
            dataModels[i++] = dataModel;
        }
        return dataModels;
    }

    @SuppressWarnings("unchecked")
    public DataModel<T>[] putDataModels(DataModel<T>[] datamodels) {

        int i = 0;
        DataModel<T> dataModelTemp = new DataModel<>();
        DataModel<T>[] replacesPages = new DataModel[datamodels.length];
        for (DataModel<T> dataModel : datamodels) {
            dataModelTemp = algo.putElement(dataModel.getDataModelId(), dataModel);
            if (dataModelTemp != null) {
                replacesPages[i++] = dataModelTemp;
            }
        }
        return replacesPages;
    }

    public void removeDataModels(Long[] ids) {
        for (Long id : ids) {
            algo.removeElement(id);
        }
    }

    //Getter and Setter

    public com.hit.algorithm.IAlgoCache<Long, DataModel<T>> getAlgo() {
        return algo;
    }

    public void setAlgo(com.hit.algorithm.IAlgoCache<Long, DataModel<T>> algo) {
        this.algo = algo;
    }

}
