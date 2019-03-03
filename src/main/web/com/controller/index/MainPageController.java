package com.controller.index;

import com.entity.HeadLine;
import com.entity.ShopCategory;
import com.service.HeadLineService;
import com.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/mainpage")
public class MainPageController {
    @Autowired
    private HeadLineService headLineService;
    @Autowired
    private ShopCategoryService shopCategoryService;

    @RequestMapping(value = "/listmainpageinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> listMainPageInfo(){
        Map<String,Object> map=new HashMap<String,Object>();
        List<ShopCategory> shopCategoryList=new ArrayList<ShopCategory>();
        try{
            shopCategoryList=shopCategoryService.getShopCategoryList(null);
            map.put("shopCategoryList",shopCategoryList);
        }catch (Exception e){
            map.put("success",false);
            map.put("errMsg",e.getMessage());
            return map;
        }
        List<HeadLine> headLineList=new ArrayList<HeadLine>();
        try{
            HeadLine headLine=new HeadLine();
            headLine.setEnableStatus(1);
            headLineList=headLineService.getHeadLineList(headLine);
            map.put("headLineList",headLineList);
        }catch (Exception e){
            map.put("success",false);
            map.put("errMsg",e.getMessage());
            return map;
        }
        map.put("success",true);
        return map;
    }
}
