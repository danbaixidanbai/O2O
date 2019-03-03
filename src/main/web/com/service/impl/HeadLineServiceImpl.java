package com.service.impl;

import com.dao.HeadLineDao;
import com.entity.HeadLine;
import com.service.HeadLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HeadLineServiceImpl implements HeadLineService {
    @Autowired
    private HeadLineDao headLineDao;
    @Override
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition) {
        return headLineDao.queryHeadLineList(headLineCondition);
    }
}
