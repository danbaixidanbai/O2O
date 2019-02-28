package com.service.test;

import com.dto.ProductExecution;
import com.entity.Product;
import com.entity.ProductCategory;
import com.entity.Shop;
import com.service.ProductService;
import com.util.BaseTest;
import com.util.MultipartUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductTest extends BaseTest {
    @Autowired
    ProductService productService;

    @Test
    public void testProductService(){
        Product product=new Product();
        Shop shop=new Shop();
        shop.setShopId(16l);
        product.setShop(shop);
        ProductCategory pc=new ProductCategory();
        pc.setProductCategoryId(1l);
        product.setProductCategory(pc);
        product.setProductName("ko1");
        product.setEnableStatus(1);
        product.setCreateTime(new Date());
        product.setPriority(20);

        File file = new File("D:/project/demo.jpg");
        File productImg1=new File("D:/project/demo1.jpg");
        File productImg2=new File("D:/project/demo2.png");

        MultipartFile multipartFile= MultipartUtil.getMultipatrFile(file);
        MultipartFile multipartFile1= MultipartUtil.getMultipatrFile(productImg1);
        MultipartFile multipartFile2= MultipartUtil.getMultipatrFile(productImg2);
        List<MultipartFile> multipartFileList=new ArrayList<MultipartFile>();
        multipartFileList.add(multipartFile1);
        multipartFileList.add(multipartFile2);
        ProductExecution pe=productService.addProduct(product,multipartFile,multipartFileList);
        System.out.println("states:"+pe.getState());
        System.out.println("statesInfo:"+pe.getStateInfo());
    }
    @Test
    public void testMotifyProduct(){
        Product product=productService.getProductById(4l);
        product.setProductName("正式修改的商品");
        File file = new File("D:/project/demo3.jpg");
        File productImg1=new File("D:/project/demo4.jpg");
        MultipartFile multipartFile= MultipartUtil.getMultipatrFile(file);
        MultipartFile multipartFile1= MultipartUtil.getMultipatrFile(productImg1);
        List<MultipartFile> multipartFileList=new ArrayList<MultipartFile>();
        multipartFileList.add(multipartFile1);
        ProductExecution pe=productService.motifyProduct(product,multipartFile,multipartFileList);
        System.out.println("states:"+pe.getState());
        System.out.println("statesInfo:"+pe.getStateInfo());
    }

}
