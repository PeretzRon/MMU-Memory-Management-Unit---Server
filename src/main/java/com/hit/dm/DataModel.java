package com.hit.dm;

@SuppressWarnings("serial")
public class DataModel<T> implements java.io.Serializable {

    Long id;
    T content;

    public DataModel() {
        super();
    }

    public DataModel(Long id, T content) { // C'tor
        this.id = id;
        this.content = content;
    }

    // equals and hashcode is to recognize between same data model by ID
    @Override
    public boolean equals(Object arg0) {
        if (arg0 == null)
            return false;

        if (arg0 == this)
            return true;

        @SuppressWarnings("unchecked")
        DataModel<T> dataModel = (DataModel<T>) arg0;
        return this.id.equals(dataModel.id);
    }

    // equals and hashcode is to recognize between same data model by ID
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "id = " + id + ", " + "content = " + content + "\n";
    }


    //Getter and Setter
    public Long getDataModelId() {
        return id;
    }

    public void setDataModelId(Long id) {
        this.id = id;
    }

    public T getContent() {
        return content;

    }

    public void setContent(T content) {
        this.content = content;
    }

}
