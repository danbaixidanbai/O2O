package com.dao.test;

import com.dao.HeadLineDao;
import com.entity.HeadLine;
import com.util.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class HeadLineTest extends BaseTest {
    @Autowired
    private HeadLineDao headLineDao;

    @Test
    public void testQueryHeadLineList(){
        List<HeadLine> headLineList=headLineDao.queryHeadLineList(new HeadLine());
        System.out.println("demo:"+headLineList.size());
    }
}
