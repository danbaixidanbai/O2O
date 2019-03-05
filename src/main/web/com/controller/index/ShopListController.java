package com.controller.index;

import com.dto.ShopExecution;
import com.entity.Area;
import com.entity.Shop;
import com.entity.ShopCategory;
import com.service.AreaService;
import com.service.ShopCategoryService;
import com.service.ShopService;
import com.util.HttpServletRequestUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/index")
public class ShopListController {

    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private ShopService shopService;

    @RequestMapping(value = "shopcategoryandarealist",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> shopCategoryAndareaList(HttpServletRequest request){
        Map<String,Object> map=new HashMap<String,Object>();
        long parentId= HttpServletRequestUtil.getLong(request,"parentId");
        List<ShopCategory> shopCategoryList=null;
        //parentId>0,说明为首页中一级类别中获取
        if(parentId>0){
            try{
                ShopCategory parent =new ShopCategory();
                parent.setShopCategoryId(parentId);
                ShopCategory shopCategory=new ShopCategory();
                shopCategory.setParent(parent);
                shopCategoryList=shopCategoryService.getShopCategoryList(shopCategory);
            }catch (Exception e){
                map.put("success",false);
                map.put("errMsg",e.getMessage());
                return map;
            }
        }else{
            try{
                shopCategoryList=shopCategoryService.getShopCategoryList(null);
            }catch (Exception e){
                map.put("success",false);
                map.put("errMsg",e.getMessage());
                return map;
            }
        }
        map.put("shopCategoryList",shopCategoryList);
        List<Area> areaList =null;
        try{
            areaList=areaService.queryArea();
        }catch (Exception e){
            map.put("success",false);
            map.put("errMsg",e.getMessage());
            return map;
        }
        map.put("areaList",areaList);
        map.put("success",true);
        return map;
    }

    @RequestMapping(value = "getshoplist",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getshopList(HttpServletRequest request){
        Map<String,Object> map=new HashMap<String,Object>();
        int pageIndex=HttpServletRequestUtil.getInt(request,"pageIndex");
        int pageSize=HttpServletRequestUtil.getInt(request,"pageSize");
        if(pageIndex>0&&pageSize>0){
            long parentId= HttpServletRequestUtil.getLong(request,"parentId");
            long shopCategoryId=HttpServletRequestUtil.getLong(request,"shopCategoryId");
            int areaId=HttpServletRequestUtil.getInt(request,"areaId");
            String shopName=HttpServletRequestUtil.getString(request,"shopName");
            Shop shopCondition= buildShopCondition(parentId,shopCategoryId,areaId,shopName);
            ShopExecution se=shopService.getShopList(shopCondition,pageIndex,pageSize);
            map.put("success",true);
            map.put("shopList",se.getShopList());
            map.put("count",se.getCount());
        }else{
            map.put("success",false);
            map.put("errMsg","empty pageSize or pageIndex");
        }
        return map;
    }

    private Shop buildShopCondition(long parentId, long shopCategoryId, int areaId, String shopName) {
        Shop shopCondition =new Shop();
        if(parentId>0){
            ShopCategory parent=new ShopCategory();
            parent.setShopCategoryId(parentId);
            ShopCategory shopCategory=new ShopCategory();
            shopCategory.setParent(parent);
            shopCondition.setShopCategory(shopCategory);
        }
        if(shopCategoryId>0){
            ShopCategory shopCategory=new ShopCategory();
            shopCategory.setShopCategoryId(shopCategoryId);
            shopCondition.setShopCategory(shopCategory);
        }
        if(areaId>0){
            Area area=new Area();
            area.setAreaId(areaId);
            shopCondition.setArea(area);
        }
        if(shopName!=null){
            shopCondition.setShopName(shopName);
        }
        return shopCondition;
    }
}
