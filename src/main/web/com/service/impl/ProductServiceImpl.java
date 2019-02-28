package com.service.impl;

import com.dao.ProductDao;
import com.dao.ProductImgDao;
import com.dto.ProductExecution;
import com.entity.Product;
import com.entity.ProductImg;
import com.enums.ProductStateEnum;
import com.exception.ProductOperationException;
import com.service.ProductService;
import com.util.ImageUtil;
import com.util.PageCalculator;
import com.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;

    @Override
    @Transactional
    public ProductExecution addProduct(Product product, MultipartFile multipartFile, List<MultipartFile> multipartFileList) throws ProductOperationException {
        if(product!=null&&product.getShop()!=null&&product.getShop().getShopId()!=null){
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            product.setEnableStatus(1);
            if(multipartFile!=null){
                addMultipartFile(product,multipartFile);
            }
            try{
                int effectNum=productDao.addProduct(product);
                if(effectNum<=0){
                    throw new ProductOperationException("创建商品失败!");
                }
            }catch (Exception e){
                throw  new ProductOperationException("商品创建失败："+e.getMessage());
            }
            if(multipartFileList!=null && multipartFileList.size()>0){
                addProductImgList(product,multipartFileList);
            }
            return new ProductExecution(ProductStateEnum.SUCCESS,product);
        }else{
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    @Override
    @Transactional
    public ProductExecution motifyProduct(Product product, MultipartFile multipartFile, List<MultipartFile> multipartFileList) throws ProductOperationException {
        if(product!=null&&product.getShop()!=null&&product.getShop().getShopId()!=null) {
            product.setLastEditTime(new Date());
            if (multipartFile != null) {
                Product tempProduct = productDao.queryProductById(product.getProductId());
                if (tempProduct.getImgAddr() != null) {
                    ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
                    addMultipartFile(product, multipartFile);
                }
            }
                if (multipartFileList != null && multipartFileList.size() > 0) {
                    deleteProductImgList(product, multipartFileList);
                    addProductImgList(product, multipartFileList);
                }
                try {
                    int effectNum = productDao.updateProduct(product);
                    if (effectNum <= 0) {
                        throw new ProductOperationException("更新商品失败！！");
                    }
                    return new ProductExecution(ProductStateEnum.SUCCESS, product);
                } catch (Exception e) {
                    throw new ProductOperationException("更新商品失败:" + e.toString());
                }
        }else throw new ProductOperationException("商品为空！！！" );

    }

    private void deleteProductImgList(Product product, List<MultipartFile> multipartFileList) {
            List<ProductImg> list=productImgDao.queryProductImgListByProductId(product.getProductId());
            for(ProductImg productImg:list){
                ImageUtil.deleteFileOrPath(productImg.getImgAddr());
            }
            productImgDao.delProductImgByProductId(product.getProductId());
    }

    @Override
    public Product getProductById(Long productId) {
        return productDao.queryProductById(productId);
    }

    @Override
    public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
        int rowIndex= PageCalculator.calculateRowIndex(pageIndex,pageSize);
        List<Product> productList=productDao.queryProductList(productCondition,rowIndex,pageSize);
        int count=productDao.queryProductCount(productCondition);
        ProductExecution productExecution=new ProductExecution();
        productExecution.setCount(count);
        productExecution.setProductList(productList);
        return productExecution;
    }

    private void addProductImgList(Product product, List<MultipartFile> multipartFileList) {
        String dest= PathUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> productImgList=new ArrayList<ProductImg>();
        if(multipartFileList!=null&&multipartFileList.size()>0){
            try{
                for(MultipartFile multipartFile:multipartFileList){
                    ProductImg productImg=new ProductImg();
                    String address=ImageUtil.generateNormalThumbnail(multipartFile,dest);
                    productImg.setImgAddr(address);
                    productImg.setProductId(product.getProductId());
                    productImg.setPriority(1);
                    productImg.setCreateTime(new Date());
                    productImgList.add(productImg);
                }
                int effectNum=productImgDao.addProductImgs(productImgList);
                product.setProductImgList(productImgList);
                if(effectNum<=0) throw new  ProductOperationException("创建商品详情图片失败");
            }catch (Exception e){
                throw new ProductOperationException("创建商品详情图片失败"+e.toString());
            }
        }
    }

    private void addMultipartFile(Product product, MultipartFile multipartFile) {
        String dest= PathUtil.getShopImagePath(product.getShop().getShopId());
        String address= ImageUtil.generateThumbnail(multipartFile,dest);
        product.setImgAddr(address);
    }
}
