package com.dto;

import com.entity.ProductCategory;
import com.enums.ProductCategoryStateEnum;

import java.util.List;

public class ProductCategoryExecution {
    private int state;
    private String stateInfo;
    private List<ProductCategory> productCategoryList;

    public ProductCategoryExecution() {
    }

    public ProductCategoryExecution(ProductCategoryStateEnum productCategoryStateEnum) {
        this.state=productCategoryStateEnum.getState();
        this.stateInfo=productCategoryStateEnum.getStateInfo();
    }

    public ProductCategoryExecution(ProductCategoryStateEnum productCategoryStateEnum, List<ProductCategory> productCategoryList) {
        this.state=productCategoryStateEnum.getState();
        this.stateInfo=productCategoryStateEnum.getStateInfo();
        this.productCategoryList=productCategoryList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public List<ProductCategory> getShopCategoryList() {
        return productCategoryList;
    }

    public void setShopCategoryList(List<ProductCategory> shopCategoryList) {
        this.productCategoryList = shopCategoryList;
    }
}
