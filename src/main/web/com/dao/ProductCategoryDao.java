package com.dao;

import com.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryDao {

    public List<ProductCategory> queryProductCategoryListByShopId(long shopId);

    public int addProductCategorys(List<ProductCategory> productCategoryList);

    public int deleteProductCategory(@Param("productCategoryId") long productCategory,@Param("shopId") long shopId);

}
