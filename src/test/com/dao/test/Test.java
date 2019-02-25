package com.dao.test;

import com.dao.AreaDao;
import com.dao.ProductCategoryDao;
import com.entity.Area;
import com.entity.ProductCategory;
import com.entity.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import com.util.BaseTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test extends BaseTest {
    @Autowired
    private AreaDao areaDao;
    @Autowired
    private ProductCategoryDao productCategoryDao;

    @org.junit.Test
    public void TestQueryArea(){
        List<Area> list=areaDao.queryArea();
        System.out.println(list.toString());
    }

    @org.junit.Test
    public void show(){
        Shop shop=new Shop();
        if(shop==null)
            System.out.println(true);
        else System.out.println(false);
    }
    @org.junit.Test
    public void testProductCategoryDao(){
       ProductCategory productCategory1=new ProductCategory();
       ProductCategory productCategory2=new ProductCategory();
       productCategory1.setProductCategoryName("测试的商品类别1");
       productCategory2.setProductCategoryName("测试的商品类别2");
       productCategory1.setPriority(3);
       productCategory2.setPriority(4);
       /*productCategory1.setCreateTime(new Date());
       productCategory2.setCreateTime(new Date());*/
       productCategory1.setShopId(2l);
       productCategory2.setShopId(2l);
       List<ProductCategory> list=new ArrayList<ProductCategory>();
       list.add(productCategory1);
       list.add(productCategory2);
       int num=productCategoryDao.addProductCategorys(list);
       System.out.println("行数："+num);

    }
    @org.junit.Test
    public void testDeleProductCategory(){
        int num=productCategoryDao.deleteProductCategory(5,2);
        System.out.println("shanchu:"+num);
    }
}
