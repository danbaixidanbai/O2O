package com.enums;

public enum ProductStateEnum {
    SUCCESS(1,"添加成功！！"),
    EMPTY(-1002,"商品为空！！！"),
    INNER_ERROR(-1001,"操作失败！！！");

    private int state;
    private  String stateInfo;

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    private ProductStateEnum(int state, String stateInfo){
        this.state=state;
        this.stateInfo=stateInfo;
    }

    public ProductStateEnum stateOf(int state){
        for(ProductStateEnum pe : values()){
            if(pe.state==state){
                return pe;
            }
        }
        return null;
    }
}
