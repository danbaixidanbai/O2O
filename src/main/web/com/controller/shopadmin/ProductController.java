package com.controller.shopadmin;

import com.dto.ProductCategoryExecution;
import com.dto.Result;
import com.entity.ProductCategory;
import com.entity.Shop;
import com.enums.ProductCategoryStateEnum;
import com.exception.ProductCategoryOperationException;
import com.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = "/shopadmin")
public class ProductController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @RequestMapping(value = "getproductcategorylist",method = RequestMethod.GET)
    @ResponseBody
    private Result<List<ProductCategory>> getProductCategory(HttpServletRequest request){
        Shop currentShop= (Shop) request.getSession().getAttribute("currentShop");
        List<ProductCategory> list=null;
        if(currentShop!=null &&currentShop.getShopId()>0){
            list=productCategoryService.getProductCategoryList(currentShop.getShopId());
            return  new Result<List<ProductCategory>>(true,list);
        }else{
            ProductCategoryStateEnum productCategoryStateEnum= ProductCategoryStateEnum.INNER_ERROR;
            return new Result<List<ProductCategory>>(false,productCategoryStateEnum.getStateInfo(),productCategoryStateEnum.getState());
        }

    }
    @RequestMapping(value = "/addproductcategorys",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addProductCategorys(@RequestBody List<ProductCategory> productCategoryList, HttpServletRequest request){
        Map<String,Object> map=new HashMap<String,Object>();
        Shop currentShop= (Shop) request.getSession().getAttribute("currentShop");
        if(productCategoryList!=null && productCategoryList.size()>0){
            for(ProductCategory pc:productCategoryList){
                pc.setShopId(currentShop.getShopId());
                pc.setCreateTime(new Date());
            }
            try{
                ProductCategoryExecution pe=productCategoryService.addProductCategorys(productCategoryList);
                if(pe.getState()==ProductCategoryStateEnum.SUCCESS.getState()){
                    map.put("success",true);
                }else{
                    map.put("success",false);
                    map.put("errMsg",pe.getStateInfo());
                }
            }catch (RuntimeException e){
                map.put("success",false);
                map.put("errMsg",e.toString());
                return map;
            }


        }else{
            map.put("success",false);
            map.put("errMsg","请至少输入一个商品类别");
        }
        return map;
    }

    @RequestMapping(value = "/removeproductcategory",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> removeProductCategory(Long productCategoryId,HttpServletRequest request){
        Map<String,Object> map=new HashMap<String,Object>();
        if(productCategoryId !=null && productCategoryId>0){
            try{
                Shop currentShop= (Shop) request.getSession().getAttribute("currentShop");
                ProductCategoryExecution pe=productCategoryService.deleteProductCategory(productCategoryId,currentShop.getShopId());
                if(pe.getState()==ProductCategoryStateEnum.SUCCESS.getState()){
                    map.put("success",true);
                }else{
                    map.put("success",false);
                    map.put("errMsg",pe.getStateInfo());
                }
            }catch (ProductCategoryOperationException e){
                map.put("success",false);
                map.put("errMsg",e.toString());
                return map;
            }
        }else{
            map.put("success",false);
            map.put("errMsg","请至少选择一个商品类别");
        }
        return map;
    }
}
