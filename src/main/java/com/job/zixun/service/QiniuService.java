package com.job.zixun.service;

import com.alibaba.fastjson.JSONObject;
import com.job.zixun.util.ToutiaoUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class QiniuService {
    private static final Logger logger = LoggerFactory.getLogger(QiniuService.class);

    String ACCESS_KEY = "Iq8aP8ykqXc0u-_HeWIltybLiC9iB7-fp-NPFwFS";
    String SECRET_KEY = "cdx87kctuG9zPYIzujUYDEtA887nED1gVdz-vMCL";
    String bucketname = "zixun-image";

    Auth auth = Auth.create(ACCESS_KEY,SECRET_KEY);

    UploadManager uploadManager = new UploadManager();

    public String getUpToken(){
        return auth.uploadToken(bucketname);
    }

    public String saveImage(MultipartFile file) throws IOException {
        try {
            int doPos = file.getOriginalFilename().lastIndexOf(".");
            if(doPos < 0){
                return null;
            }
            String fileExt = file.getOriginalFilename().substring(doPos+1).toLowerCase();
            if(!ToutiaoUtil.isFileAllowed(fileExt))
                return null;

            String fileName = UUID.randomUUID().toString().replaceAll("-","")+ "." + fileExt;

            Response res = uploadManager.put(file.getBytes(),fileName, getUpToken());
//            System.out.println(res.toString());
            if(res.isOK() && res.isJson()){
                String key =  JSONObject.parseObject(res.bodyString()).get("key").toString();
                return ToutiaoUtil.QINIU_DOMAIN_PREFIX + key;
            }else{
                logger.error("七牛异常" + res.bodyString());
                return null;
            }
        } catch (QiniuException e){
            logger.error("七牛异常" + e.getMessage());
            return null;
        }
    }
}
