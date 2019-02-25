package com.service.impl;

import com.dao.ShopDao;
import com.dto.ShopExecution;
import com.entity.Shop;
import com.enums.ShopStateEnum;
import com.exception.ShopException;
import com.exception.ShopOperationException;
import com.service.ShopService;
import com.util.ImageUtil;
import com.util.PageCalculator;
import com.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopDao shopDao;
    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, MultipartFile shopImg) {
       if(shop==null) return new ShopExecution(ShopStateEnum.NULL_SHOP);
       try{
           shop.setEnableStatus(0);
           shop.setCreateTime(new Date());
           shop.setLastEditTime(new Date());
           int num=0;
           num=shopDao.addShop(shop);
           if(num<=0){
               throw new ShopException("添加店铺失败");
           }else{
               if(shopImg!=null){
                   //存储图片
                   try{
                      addShopImg(shop,shopImg);
                   }catch (Exception e){
                       throw new ShopException("add shopImg error:"+e.getMessage());
                   }
                   num=0;
                   num=shopDao.updateShop(shop);
                   if(num<=0){
                       throw new ShopException("更新图片地址失败");
                   }
               }

           }
       }catch (Exception e){
            throw new ShopException("add shop error:"+e.getMessage());
       }
       return new ShopExecution(ShopStateEnum.CHECK,shop);
    }

    @Override
    public Shop getByShopId(long shopId) {
        return shopDao.queryByShopId(shopId);
    }
    @Transactional
    @Override
    public ShopExecution modifyShop(Shop shop, MultipartFile multipartFile) throws ShopOperationException {
        if(shop==null||shop.getShopId()==null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }else{
            try {
                if (multipartFile != null) {
                    Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                    if (tempShop.getShopImg() != null) {
                        ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                    }
                    addShopImg(shop, multipartFile);
                }
                shop.setLastEditTime(new Date());
                int effectedNum = shopDao.updateShop(shop);
                if (effectedNum <= 0) {
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                } else {
                    shop = shopDao.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS, shop);
                }
            }catch (Exception e){
                throw new ShopOperationException("modifyShop error"+e.getMessage());
            }
        }
    }

    @Override
    public ShopExecution getShopList(Shop shopCodition, int pageIndex, int pageSize) {
        int rowIndex= PageCalculator.calculateRowIndex(pageIndex,pageSize);
        List<Shop> shopList=shopDao.queryShopList(shopCodition,rowIndex,pageSize);
        int count=shopDao.queryShopCount(shopCodition);
        ShopExecution shopExecution=new ShopExecution();
        if(shopList!=null){
            shopExecution.setShopList(shopList);
            shopExecution.setCount(count);
        }else shopExecution.setState(ShopStateEnum.INNER_ERROR.getState());
        return shopExecution;
    }

    private void addShopImg(Shop shop, MultipartFile shopImg) {
        //获取图片id构造目录
        String dest=PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr=ImageUtil.generateThumbnail(shopImg,dest);
        shop.setShopImg(shopImgAddr);
    }
}
