package com.dao;

import com.entity.HeadLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HeadLineDao {
    public List<HeadLine> queryHeadLineList(@Param("headLineCondition") HeadLine headLineCondition);
}
