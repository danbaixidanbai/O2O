package com.util;

public class PathUtil {
    private static String seperator=System.getProperty("file.seperator");

    public static String getImgBasePath(){
        String basePath="D:/project/image/";
        //basePath=basePath.replace("/",seperator);
        return basePath;
    }

    public static String getShopImagePath(long shopId){
            String imagePath="upload/item/shop/"+shopId+"/";
           // imagePath=imagePath.replace("/",seperator);
        return imagePath;
    }
}
