package com.service.impl;

import com.dao.AreaDao;
import com.entity.Area;
import com.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaDao areaDao;
    @Override
    public List<Area> queryArea() {
        return areaDao.queryArea();
    }
}
