package com.controller;

import com.entity.Area;
import com.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/test")
public class AreaController {

    Logger logger= LoggerFactory.getLogger(AreaController.class);
    @Autowired
    private AreaService areaService;

    @RequestMapping(value="/areas",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> Areas(){
        logger.info("-------star---------");
        Long startTime=System.currentTimeMillis();
        Map<String,Object> map=new HashMap<String,Object>();
        List<Area> areaList =new ArrayList<Area>();
        try{
            areaList=areaService.queryArea();
            System.out.println(areaList.toString());
            map.put("row",areaList);
            map.put("total",areaList.size());
        }catch (Exception e){
            e.getStackTrace();
            map.put("error","获取信息出错！");
        }
        Long endTime=System.currentTimeMillis();
        logger.error("test error!!!");
        logger.debug("costTime:[{}ms]",endTime-startTime);
        logger.info("-------end---------");
        return map;
    }
}
