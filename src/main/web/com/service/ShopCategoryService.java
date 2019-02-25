package com.service;

import com.entity.ShopCategory;

import java.util.List;

public interface ShopCategoryService {
    public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
