package com.dao.test;

import com.dao.ProductDao;
import com.dao.ProductImgDao;
import com.entity.Product;
import com.entity.ProductCategory;
import com.entity.ProductImg;
import com.entity.Shop;
import com.util.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductTest extends BaseTest {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void testProductDao(){
        ProductCategory pc=new ProductCategory();
        Shop shop=new Shop();
        shop.setShopId(2l);
        pc.setProductCategoryId(2l);
        Product product =new Product();
        product.setCreateTime(new Date());
        product.setProductName("双皮奶1");
        product.setNormalPrice("100");
        product.setPromotionPrice("5");
        product.setPriority(1);
        product.setEnableStatus(0);
        product.setProductCategory(pc);
        product.setShop(shop);
        int num=productDao.addProduct(product);
        System.out.println("num:"+num);
        System.out.println("productId:"+product.getProductId());

    }

    @Test
    public void TestProductImg(){
        ProductImg productImg1=new ProductImg();
        ProductImg productImg2=new ProductImg();
        productImg1.setImgAddr("test1");
        productImg2.setImgAddr("test2");
        productImg1.setProductId(1l);
        productImg2.setProductId(1l);
        productImg1.setPriority(1);
        productImg2.setPriority(2);
        productImg1.setCreateTime(new Date());
        productImg2.setCreateTime(new Date());
        List<ProductImg> productImgList=new ArrayList<ProductImg>();
        productImgList.add(productImg1);
        productImgList.add(productImg2);
        int num=productImgDao.addProductImgs(productImgList);
        System.out.println("num:"+num);
    }
}
