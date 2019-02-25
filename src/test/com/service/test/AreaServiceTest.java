package com.service.test;

import com.entity.Area;
import com.service.AreaService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.util.BaseTest;

import java.util.List;

public class AreaServiceTest extends BaseTest {
    @Autowired
    private AreaService areaService;
    @Test
    public  void TestAreaService(){
        List<Area> areaList=areaService.queryArea();
        for (Area area: areaList
             ) {
                System.out.println(area.getAreaName());
        }
    }
}
