package com.dao.test;

import com.dao.ShopDao;
import com.entity.Area;
import com.entity.PersonInfo;
import com.entity.Shop;
import com.entity.ShopCategory;
import com.util.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class ShopDaoTest extends BaseTest {
    @Autowired
    private ShopDao shopDao;


    @Test
    public void testQueryByShopId(){
        long shopId=2;
        Shop shop=shopDao.queryByShopId(shopId);
        System.out.println("shopName:"+shop.getShopName());
        System.out.println("areaId:"+shop.getArea().getAreaId());
        System.out.println("areaName:"+shop.getArea().getAreaName());
    }

    @Test
    public void addShopTest(){
        Area area =new Area();
        area.setAreaId(3);
        PersonInfo personInfo=new PersonInfo();
        personInfo.setUserId(1L);
        ShopCategory shopCategory=new ShopCategory();
        shopCategory.setShopCategoryId(1L);

        Shop shop =new Shop();
        shop.setShopName("test");
        shop.setShopDesc("test");
        shop.setShopAddr("test");
        shop.setPhone("test");
        shop.setAdvice("test");
        shop.setShopImg("test");
        shop.setPriority(1);
        shop.setCreateTime(new Date());
        shop.setEnableStatus(0);
        shop.setOwner(personInfo);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);

        int test=shopDao.addShop(shop);
        System.out.println(test);
    }
    @Test
    public void updateShopTest(){
        Shop shop =new Shop();
        shop.setShopId(2l);
        shop.setShopName("test!!!");
        shop.setShopDesc("测试test");
        shop.setLastEditTime(new Date());
        int test=shopDao.updateShop(shop);
        System.out.println(test);
    }


    @Test
    public  void testQueryShopList(){
        Shop shopCondition =new Shop();
        PersonInfo owner=new PersonInfo();
        owner.setUserId(1l);
        Area area=new Area();
        area.setAreaId(4);
        shopCondition.setOwner(owner);
        shopCondition.setArea(area);
        List<Shop> shopList=shopDao.queryShopList(shopCondition,0,3);
        int count=shopDao.queryShopCount(shopCondition);
        System.out.println("店铺的数量为："+shopList.size());
        System.out.println("店铺的zong数量为："+count);
    }
}
