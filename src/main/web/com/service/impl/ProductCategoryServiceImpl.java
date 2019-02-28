package com.service.impl;

import com.dao.ProductCategoryDao;
import com.dao.ProductDao;
import com.dto.ProductCategoryExecution;
import com.entity.ProductCategory;
import com.enums.ProductCategoryStateEnum;
import com.exception.ProductCategoryOperationException;
import com.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Autowired
    private ProductDao productDao;

    @Override
    public List<ProductCategory> getProductCategoryList(long shopId) {
        return productCategoryDao.queryProductCategoryListByShopId(shopId);
    }

    @Override
    @Transactional
    public ProductCategoryExecution addProductCategorys(List<ProductCategory> productCategoryList)
            throws ProductCategoryOperationException {
        if(productCategoryList!=null && productCategoryList.size()>0){
            try{
                int effectNum=productCategoryDao.addProductCategorys(productCategoryList);
                if(effectNum>0){
                    return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
                }else throw new ProductCategoryOperationException("店铺类别创建失败！！！");
            }catch (ProductCategoryOperationException e){
                throw new  ProductCategoryOperationException("addProductCategory Error:"+e.getMessage());
            }
        }else return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
    }

    @Override
    @Transactional
    public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) throws ProductCategoryOperationException {
        try{
            int effectNum=productDao.updateProductCategoryToNull(productCategoryId);
            if(effectNum<0) throw new ProductCategoryOperationException("商品类别删除失败！！！");
        }catch (Exception e){
            throw new ProductCategoryOperationException("deleteProductCategoryToNullError"+e.toString());
        }
        try{
            int effectNum=productCategoryDao.deleteProductCategory(productCategoryId,shopId);
            if (effectNum<=0){
                throw new ProductCategoryOperationException("店铺类别删除失败！！！");
            }else return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
        }catch (Exception e){
            throw new ProductCategoryOperationException("delete productCategory error:"+e.getMessage());
        }
    }
}
