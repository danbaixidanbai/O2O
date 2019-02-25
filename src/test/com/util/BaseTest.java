package com.util;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/*
*
* 配置spring和junit整合,junit启动加载springIOC容器
*
* */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件的位置
@ContextConfiguration({"classpath:spring-config.xml"})
public class BaseTest {

}
