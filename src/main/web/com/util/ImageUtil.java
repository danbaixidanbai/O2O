package com.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Position;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ImageUtil {
    //获取相对路径
    private static String basePath=Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random r=new Random();
    public static String generateThumbnail(MultipartFile thumbnail,String targetAddr){
        String realFileName=getRandomFileName();
        String extension=getFileExtensionName(thumbnail);
        makeDirPath(targetAddr);
        String relativeAddr=targetAddr+realFileName+extension;
        File dest=new File(PathUtil.getImgBasePath()+relativeAddr);
        try {
            Thumbnails.of(thumbnail.getInputStream()).size(200,200)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath+"watermark.jpg")),0.25f)
                    .outputQuality(0.8f).toFile(dest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return relativeAddr;
    }
    /*
    * 创建目标路径所涉及的目录
    *
     */
    private static void makeDirPath(String targetAddr) {
        String realFilePath=PathUtil.getImgBasePath()+targetAddr;
        File dirPath=new File(realFilePath);
        if(!dirPath.exists()){
            dirPath.mkdirs();
        }
    }

    /*
    * 获取输入流的扩展名
    * */
    private static String getFileExtensionName(MultipartFile thumbnail) {
        String oringinalFileName=thumbnail.getOriginalFilename();
        if(oringinalFileName==null|| oringinalFileName.equals("")){
            oringinalFileName=thumbnail.getName();
        }
        return oringinalFileName.substring(oringinalFileName.lastIndexOf("."));
    }
    //随机数+时间生成名字
    private static String getRandomFileName() {
        int rannum=r.nextInt(89999)+10000;
        String nowTimeStr=simpleDateFormat.format(new Date());
        return rannum+nowTimeStr;
    }
    //删除图片及目录
    public static  void deleteFileOrPath(String filePath){
        File file =new File(PathUtil.getImgBasePath()+filePath);
        if(file.exists()){
            if(file.isDirectory()){
                File files[] =file.listFiles();
                for (int i=0;i<files.length;i++){
                    files[i].delete();
                }
            }
        }
        file.delete();
    }
}
