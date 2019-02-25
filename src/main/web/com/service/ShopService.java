package com.service;

import com.dto.ShopExecution;
import com.entity.Shop;
import com.exception.ShopOperationException;
import org.springframework.web.multipart.MultipartFile;

public interface ShopService {
    public ShopExecution addShop(Shop shop, MultipartFile shopImg);
    public Shop  getByShopId(long shopId);

    public ShopExecution modifyShop(Shop shop,MultipartFile multipartFile) throws ShopOperationException;
    public ShopExecution getShopList(Shop shopCodition,int pageIndex,int pageSize);
}
