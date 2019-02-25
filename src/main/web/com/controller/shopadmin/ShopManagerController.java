package com.controller.shopadmin;

import com.dto.ShopExecution;
import com.entity.Area;
import com.entity.PersonInfo;
import com.entity.Shop;
import com.entity.ShopCategory;
import com.enums.ShopStateEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.AreaService;
import com.service.ShopCategoryService;
import com.service.ShopService;
import com.util.CodeUtil;
import com.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/shopadmin")
public class ShopManagerController {

    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;

    @RequestMapping(value="/getshopmanagementinfo",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getShopManagementInfo(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        long shopId=HttpServletRequestUtil.getLong(request,"shopId");
        if(shopId<=0){
            Object currentShopObj=request.getSession().getAttribute("currentShop");
            if(currentShopObj==null){
                map.put("redirect",true);
                map.put("url","/shopadmin/shoplist");
            }else{
                Shop currentShop=(Shop) currentShopObj;
                map.put("redirect",false);
                map.put("shopId",currentShop.getShopId());
            }
        }else{
            Shop currentShop=new Shop();
            currentShop.setShopId(shopId);
            request.getSession().setAttribute("currentShop",currentShop);
            map.put("redirect",false);
            map.put("shopId",shopId);
        }
        return  map;
    }
    @RequestMapping(value="/getshopinfo",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getShopInfo(HttpServletRequest request){
        Map<String,Object> map=new HashMap<String, Object>();
        Long shopId=HttpServletRequestUtil.getLong(request,"shopId");
        if(shopId>-1){
            try{
                Shop shop=shopService.getByShopId(shopId);
                List<Area> areaList =areaService.queryArea();
                map.put("shop",shop);
                map.put("areaList",areaList);
                map.put("success",true);
            }catch (Exception e){
                map.put("success",false);
                map.put("errMsg",e.toString());
            }
        }else{
            map.put("success",false);
            map.put("errMsg","empty shopId");
        }

        return map;
    }

    @RequestMapping(value="/getshoplist",method=RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getShopList(HttpServletRequest request){
        Map<String,Object> map =new HashMap<String,Object>();
        PersonInfo user=new PersonInfo();
        user.setUserId(1l);
        user.setName("tom");
        request.getSession().setAttribute("user",user);
        user=(PersonInfo) request.getSession().getAttribute("user");
        try{
            Shop shopCondition=new Shop();
            shopCondition.setOwner(user);
            ShopExecution shopExecution=shopService.getShopList(shopCondition,1,100);
            map.put("success",true);
            map.put("shopList",shopExecution.getShopList());
            map.put("shopCount",shopExecution.getCount());
            map.put("user",user);
        }catch (Exception e){
            map.put("success",false);
            map.put("errMsg",e.getMessage());
        }
        return map;
    }

    @RequestMapping(value="/getshopinitinfo",method=RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getShopInitInfo(){
        Map<String,Object> map =new HashMap<String,Object>();
        List<Area> areaList=new ArrayList<Area>();
        List<ShopCategory> shopCategoryList=new ArrayList<ShopCategory>();
        try {
            areaList=areaService.queryArea();
            shopCategoryList=shopCategoryService.getShopCategoryList(new ShopCategory());
            map.put("areaList",areaList);
            map.put("shopCategoryList",shopCategoryList);
            map.put("success",true);

        }catch (Exception e){
            map.put("success",false);
            map.put("errMsg",e.getMessage());
        }

        return  map;
    }


    @ResponseBody
    @RequestMapping(value="/modifyshop",method= RequestMethod.POST)
    public Map<String,Object> modifyShop(HttpServletRequest request){
        Map<String,Object> map=new HashMap<String,Object>();
        if(!CodeUtil.checkVerifyCode(request)){
            map.put("success",false);
            map.put("errMsg","验证码错误");
            return map;
        }
        String shopStr= HttpServletRequestUtil.getString(request,"shopStr");
        ObjectMapper mapper=new ObjectMapper();
        Shop shop=null;
        MultipartHttpServletRequest mulRequest=request instanceof MultipartHttpServletRequest?(MultipartHttpServletRequest) request : null;
        MultipartFile file=mulRequest.getFile("shopImg");
        try{
            shop=mapper.readValue(shopStr,Shop.class);
        }catch(Exception e){
            map.put("success",false);
            map.put("errMsg",e.getMessage());
            return map;
        }

        if(shop!=null&&shop.getShopId()!=null){
            ShopExecution shopExecution;
            if(file==null){
                shopExecution=shopService.modifyShop(shop,null);
            }else{
                shopExecution=shopService.modifyShop(shop,file);
            }
            if(shopExecution.getState()== ShopStateEnum.SUCCESS.getState()){
                map.put("success",true);
            }else {
                map.put("success",false);
                map.put("errMsg",shopExecution.getState());
            }
            return map;
        }else{
            map.put("success",false);
            map.put("errMsg","请输入店铺id！！！");
            return map;
        }

    }


    @ResponseBody
    @RequestMapping(value="/registershop",method= RequestMethod.POST)
    public Map<String,Object> registerShop(HttpServletRequest request){
        Map<String,Object> map=new HashMap<String,Object>();
        if(!CodeUtil.checkVerifyCode(request)){
            map.put("success",false);
            map.put("errMsg","验证码错误");
            return map;
        }
        String shopStr= HttpServletRequestUtil.getString(request,"shopStr");
        ObjectMapper mapper=new ObjectMapper();
        Shop shop=null;
        MultipartHttpServletRequest mulRequest=request instanceof MultipartHttpServletRequest?(MultipartHttpServletRequest) request : null;
        MultipartFile file=mulRequest.getFile("shopImg");
        try{
            shop=mapper.readValue(shopStr,Shop.class);
        }catch (Exception e){
            map.put("success",false);
            map.put("errMsg",e.getMessage());
            return map;
        }
        if(file==null) {
            map.put("success",false);
            map.put("errMsg","上传的图片不能为空！！！");
            return map;
        }
        if(shop!=null&&file!=null){
            PersonInfo owner=(PersonInfo)request.getSession().getAttribute("user");
            shop.setOwner(owner);
            ShopExecution shopExecution=shopService.addShop(shop,file);
            if(shopExecution.getState()== ShopStateEnum.CHECK.getState()){
                map.put("success",true);
                List<Shop> shopList =(List<Shop>) request.getSession().getAttribute("shopList");
                if(shopList==null||shopList.size()==0){
                    shopList=new ArrayList<Shop>();
                }
                shopList.add(shopExecution.getShop());
                request.getSession().setAttribute("shopList",shopList);
            }else {
                map.put("success",false);
                map.put("errMsg",shopExecution.getState());
            }
            return map;
        }else{
            map.put("success",false);
            map.put("errMsg","店铺信息不为空！！！");
            return map;
        }

    }
}
