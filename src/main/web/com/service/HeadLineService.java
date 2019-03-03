package com.service;

import com.entity.HeadLine;

import java.util.List;

public interface HeadLineService {
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition);
}
