package com.zj.ocr.network;

import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.zj.ocr.network.ResponseHandler;
import com.zj.ocr.network.HttpClient;
import com.zj.ocr.utils.ShareUtil;

/**
 * create by zj on 2018/12/3
 *
 * 百度ai接口
 */
public class AiApi {
    //文字识别（基础根据图片string）
    public static RequestHandle getGeneralBasic(String image,ResponseHandler responseHandler){
        RequestParams requestParams=new RequestParams();
        requestParams.put("image",image);
        requestParams.put("access_token", ShareUtil.getInstance().getString("token",""));
        return HttpClient.doPost("general_basic",requestParams,responseHandler);
    }
    //文字识别（基础根据图片URL）
    public static RequestHandle getGeneralBasicurl(String url,ResponseHandler responseHandler){
        RequestParams requestParams=new RequestParams();
        requestParams.put("url",url);
        requestParams.put("access_token",ShareUtil.getInstance().getString("token",""));
        return HttpClient.doPost("general_basic",requestParams,responseHandler);
    }

    //文字识别（高精度根据图片string）
    public static RequestHandle getAccurateBasic(String image,ResponseHandler responseHandler){
        RequestParams requestParams=new RequestParams();
        requestParams.put("image",image);
        requestParams.put("access_token", ShareUtil.getInstance().getString("token",""));
        return HttpClient.doPost("accurate_basic",requestParams,responseHandler);
    }






}
