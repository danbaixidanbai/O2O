package com.dao;

import com.entity.ProductImg;

import java.util.List;

public interface ProductImgDao {
    public int addProductImgs(List<ProductImg> productImgList);
    public int delProductImgByProductId(long productId);

    public List<ProductImg> queryProductImgListByProductId(Long productId);
}
