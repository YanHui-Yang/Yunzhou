package com.yyh.gathering.config;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.yyh.entity.QiNiuYun;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;

/**
 * Author: Tong
 * Date: 2021/5/7
 * Description:
 */
@Data
@Component
public class QiNiuYunConfig {
    private String accessKey = QiNiuYun.ACCESSKEY;
    private String secretKey = QiNiuYun.SECRETKEY;
    private String bucket = QiNiuYun.BUCKET;
    private String path = QiNiuYun.PATH;

    public String uploadImgToQiNiu(FileInputStream file, String filename) {
        // 构造一个带指定Zone对象的配置类，注意后面的zone各个地区不一样的
        Configuration cfg = new Configuration(Zone.zone2());
        // 其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        // 生成**
        Auth auth = Auth.create(accessKey, secretKey);
        try {
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(file, filename, upToken, null, null);
                // 解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

                // 这个returnPath是获得到的外链地址,通过这个地址可以直接打开图片
                String returnPath = getPath() + "/" + putRet.key;
                System.out.println(returnPath);
                return returnPath;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
