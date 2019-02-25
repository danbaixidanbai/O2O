package com.service;

import com.dto.ProductCategoryExecution;
import com.entity.ProductCategory;
import com.exception.ProductCategoryOperationException;

import java.util.List;

public interface ProductCategoryService {
    public List<ProductCategory> getProductCategoryList(long shopId);
    public ProductCategoryExecution addProductCategorys(List<ProductCategory> productCategory) throws ProductCategoryOperationException;
    public ProductCategoryExecution deleteProductCategory(long shopCategoryId,long shopId) throws ProductCategoryOperationException;
}
