package com.toyo.partsmanagement;

class ListItem {
    private long id = 0;
    private int idSql = 0;
    private String typeNo = null;
    private String useModel = null;
    private String amount= null;
    private String unit = null;
    protected String place = null;

    long getId(){ return id; }
    int getIdSql(){ return idSql; }
    String getTypeNo(){ return typeNo; }
    String getUseModel(){ return useModel; }
    String getAmount(){ return amount; }
    String getUnit(){ return unit; }
    String getPlace(){ return  place; }

    void setId(long id){ this.id = id; }
    void setIdSql(int idSql){ this.idSql = idSql; }
    void setTypeNo(String typeNo){ this.typeNo = typeNo; }
    void setUseModel(String useModel){ this.useModel = useModel; }
    void setAmount(String amount){ this.amount = amount; }
    void setUnit(String unit){ this.unit = unit; }
    void setPlace(String place){ this.place = place; }
}
