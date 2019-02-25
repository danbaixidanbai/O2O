package com.service.test;

import com.dto.ShopExecution;
import com.entity.Area;
import com.entity.PersonInfo;
import com.entity.Shop;
import com.entity.ShopCategory;
import com.enums.ShopStateEnum;
import com.service.ShopService;
import com.util.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

public class ShopServiceTest extends BaseTest {
    @Autowired
    private ShopService shopService;

    @Test
    public void testModifyShop(){
        Shop shop =new Shop();
        shop.setShopId(25L);
        shop.setShopName("修改后的店铺");
        File file = new File("D:/project/demo.jpg");

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        MultipartFile multipartFile=null;
        try {
            multipartFile = new MockMultipartFile(file.getName(),inputStream);


        } catch (IOException e) {
            e.printStackTrace();
        }

        ShopExecution shopExecution=shopService.modifyShop(shop,multipartFile);
        System.out.println("shopName:"+shopExecution.getShop().getShopName());
        System.out.println("shopImg:"+shopExecution.getShop().getShopImg());
    }


    @Test
    public void testAddSop(){
        Area area =new Area();
        area.setAreaId(3);
        PersonInfo personInfo=new PersonInfo();
        personInfo.setUserId(1L);
        ShopCategory shopCategory=new ShopCategory();
        shopCategory.setShopCategoryId(1L);

        Shop shop =new Shop();
        shop.setShopName("test3");
        shop.setShopDesc("test3");
        shop.setShopAddr("test3");
        shop.setPhone("test3");
        shop.setAdvice("test3");
        shop.setShopImg("test3");
        shop.setPriority(1);
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setOwner(personInfo);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        File file = new File("D:/project/demo.jpg");

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        MultipartFile multipartFile=null;
        try {
             multipartFile = new MockMultipartFile(file.getName(),inputStream);


        } catch (IOException e) {
            e.printStackTrace();
        }
        ShopExecution se=shopService.addShop(shop,multipartFile);
        System.out.println(se.toString());
    }

}
