package com.dao;

import com.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ProductDao {
    public int addProduct(Product product);

    public Product queryProductById(long productId);

    public int updateProduct(Product product);

    public List<Product> queryProductList(@Param("productCondition")Product productCondition,@Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);

    public int queryProductCount(@Param("productCondition")Product productCondition);

    public int updateProductCategoryToNull(Long productCategoryId);

}
