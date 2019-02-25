package com.enums;

import com.entity.ProductCategory;

public enum ProductCategoryStateEnum {
    SUCCESS(1,"操作成功"),
    INNER_ERROR(-1001,"操作失败"),
    EMPTY_LIST(-1002,"添加数少于1");


    private int state;
    private String stateInfo;

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    private ProductCategoryStateEnum(int state, String stateInfo){
        this.state=state;
        this.stateInfo=stateInfo;
    }

    public static ProductCategoryStateEnum stateOF(int state){
        for(ProductCategoryStateEnum productCategoryStateEnum:values()){
            if(productCategoryStateEnum.getState()==state)
                return productCategoryStateEnum;
        }
        return null;
    }
}
