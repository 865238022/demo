package top.huzhurong.demo;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

/**
 * Created by 竹 on 2017/10/6.
 */
public class UploadLocalImage {
    public static void main(String [] args){
        //地区配置
        Configuration cfg = new Configuration(Zone.zone0());
        //文件上传管理
        UploadManager uploadManager = new UploadManager(cfg);
        //访问码   密钥  存储位置
        String accessKey = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
        String secretKey = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
        String bucket = "just-use-it-store-picture";
        //本地文件路径
        String localFilePath = "PieChart.jpeg";
        //CDN上图片路径
        String key = "用户名/时间.jpeg";
        //得到授权码
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket,null,10*1000*6,null);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);//在修饰修饰直接生成的就是返回的外链
            //在这里将外链存储到redis的服务器中去
            System.out.println(putRet.hash);//对应文件的hash校验码
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
                }
        }
    }
}
