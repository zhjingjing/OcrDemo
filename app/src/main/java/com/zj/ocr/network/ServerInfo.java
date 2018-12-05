package com.zj.ocr.network;


import com.zj.orc.BuildConfig;

/**
 * create by zj on 2018/11/9
 */
public class ServerInfo {

    public interface   ServerHost{
        String DEVELOPMENT="https://aip.baidubce.com/rest/2.0/ocr/v1/";
        String ONLINE="https://aip.baidubce.com/rest/2.0/ocr/v1/";
    }

    public static String getServerAddress(){
        if (BuildConfig.DEBUG){
            return ServerHost.DEVELOPMENT;
        }else{
            return ServerHost.ONLINE;
        }
    }

}
