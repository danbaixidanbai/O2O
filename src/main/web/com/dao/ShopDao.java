package com.dao;

import com.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopDao {
    public int addShop(Shop shop);

    public int updateShop(Shop shop);

    public Shop queryByShopId(long shopId);

    public List<Shop> queryShopList(@Param("shopCondition")Shop shopCondition,@Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);

    public int queryShopCount(@Param("shopCondition")Shop shopCondition);

}
