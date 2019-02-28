package com.controller.shopadmin;

import com.dto.ProductExecution;
import com.entity.Product;
import com.entity.ProductCategory;
import com.entity.Shop;
import com.enums.ProductStateEnum;
import com.exception.ProductCategoryOperationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.ProductCategoryService;
import com.service.ProductService;
import com.util.CodeUtil;
import com.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/shopadmin")
public class ProductManagementController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @RequestMapping(value = "/getproductlistbyshop",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getProductListByShop(HttpServletRequest request){
        Map<String,Object> map =new HashMap<String,Object>();
        int pageIndex=HttpServletRequestUtil.getInt(request,"pageIndex");
        int pageSize=HttpServletRequestUtil.getInt(request,"pageSize");
        Shop currentShop= (Shop) request.getSession().getAttribute("currentShop");
        if(currentShop!=null&&currentShop.getShopId()!=null&&pageIndex>-1&&pageSize>-1){
            long productCategoryId=HttpServletRequestUtil.getLong(request,"productCategoryId");
            String productName=HttpServletRequestUtil.getString(request,"productName");
            Product productCondition=new Product();
            productCondition.setShop(currentShop);
            if(productCategoryId>-1){
                ProductCategory productCategory=new ProductCategory();
                productCategory.setProductCategoryId(productCategoryId);
                productCondition.setProductCategory(productCategory);
            }
            if(productName!=null) productCondition.setProductName(productName);
            ProductExecution pe=productService.getProductList(productCondition,pageIndex,pageSize);
            map.put("productList",pe.getProductList());
            map.put("count",pe.getCount());
            map.put("success",true);


        }else{
            map.put("success",false);
            map.put("errMsg","empty pageSize or pageIndex or shopId");
        }
        return map;
    }

    @RequestMapping(value = "/modifyproduct",method= RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> motifyProduct(HttpServletRequest request){
        Map<String,Object> map =new HashMap<String,Object>();
        boolean statusChange =HttpServletRequestUtil.getBoolean(request,"statusChange");
        if(!statusChange){
            if(!CodeUtil.checkVerifyCode(request)){
                map.put("success",false);
                map.put("errMsg","验证码错误");
                return map;
            }
        }
        ObjectMapper mapper=new ObjectMapper();
        Product product=null;
        String productStr= HttpServletRequestUtil.getString(request,"productStr");
        MultipartHttpServletRequest mulRequest=null;
        MultipartFile multipartFile=null;
        List<MultipartFile> multipartFileList=new ArrayList<MultipartFile>();
        try{
            if(request instanceof MultipartHttpServletRequest){
                mulRequest=(MultipartHttpServletRequest)request;
                multipartFile=mulRequest.getFile("productImg");
                for(int i=0;i<6;i++){
                    MultipartFile mf=mulRequest.getFile("productImgs"+i);
                    if(mf!=null){
                        multipartFileList.add(mf);
                    }else{
                        break;
                    }
                }
            }
        }catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.toString());
            return map;
        }
        try{
            product=mapper.readValue(productStr,Product.class);
        }catch (Exception e){
            map.put("success", false);
            map.put("errMsg", e.toString());
            return map;
        }
        if(product!=null){
            try{
                Shop currentShop= (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);
                //执行添加操作
                ProductExecution pe=productService.motifyProduct(product,multipartFile,multipartFileList);
                if(pe.getState()== ProductStateEnum.SUCCESS.getState()){
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
            map.put("errMsg","请输入商品信息");
            return map;
        }
        return map;
    }

    @RequestMapping(value = "/getproductbyid",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getProductById(@RequestParam Long productId){
        Map<String,Object> map =new HashMap<String,Object>();
        if(productId>-1){
            Product product=productService.getProductById(productId);
            List<ProductCategory> productCategoryList=productCategoryService.getProductCategoryList(product.getShop().getShopId());
            map.put("product",product);
            map.put("productCategoryList",productCategoryList);
            map.put("success",true);
        }else{
            map.put("success",false);
            map.put("errMsg","empty productId");

        }
        return map;
    }

    @RequestMapping(value = "/addproduct",method= RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> addProduct(HttpServletRequest request){
        Map<String,Object> map =new HashMap<String,Object>();
        if(!CodeUtil.checkVerifyCode(request)){
            map.put("success",false);
            map.put("errMsg","验证码错误");
            return map;
        }
        ObjectMapper mapper=new ObjectMapper();
        Product product=null;
        String productStr= HttpServletRequestUtil.getString(request,"productStr");
        MultipartHttpServletRequest mulRequest=null;
        MultipartFile multipartFile=null;
        List<MultipartFile> multipartFileList=new ArrayList<MultipartFile>();
        try{
            if(request instanceof MultipartHttpServletRequest){
                mulRequest=(MultipartHttpServletRequest)request;
                multipartFile=mulRequest.getFile("productImg");
                if(multipartFile==null){
                    map.put("success",false);
                    map.put("errMsg","商品图片不能为空");
                    return map;
                }
                for(int i=0;i<6;i++){
                    MultipartFile mf=mulRequest.getFile("productImgs"+i);
                    if(mf!=null){
                        multipartFileList.add(mf);
                    }else{
                        break;
                    }
                }
                if(multipartFileList.size()<=0){
                    map.put("success",false);
                    map.put("errMsg","商品详情图片不能为空");
                    return map;
                }

            }else{
                map.put("success",false);
                map.put("errMsg","上传的图片不能为空");
                return map;
            }
        }catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.toString());
            return map;
        }
        try{
            product=mapper.readValue(productStr,Product.class);
        }catch (Exception e){
            map.put("success", false);
            map.put("errMsg", e.toString());
            return map;
        }
        if(product!=null){
            try{
                Shop currentShop= (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);
                //执行添加操作
                ProductExecution pe=productService.addProduct(product,multipartFile,multipartFileList);
                if(pe.getState()== ProductStateEnum.SUCCESS.getState()){
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
            map.put("errMsg","请输入商品信息");
            return map;
        }
        return map;
    }
}
