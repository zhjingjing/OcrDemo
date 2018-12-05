package com.zj.ocr.network;

import android.text.TextUtils;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.zj.ocr.App;
import com.zj.ocr.utils.NetUtil;
import com.zj.ocr.utils.ShareUtil;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;

/**
 * create by zj on 2018/11/9
 */
public class HttpClient {
    private static final String TAG = "HttpClient";
    //contentType
    private static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";

    //创建实例
    private static final AsyncHttpClient ASYNC_CLIENT = new AsyncHttpClient();

    //可在此处配置header，例如机型，版本号，umengType等
    static {
        ASYNC_CLIENT.setTimeout(20000);
    }


    //get方法
    public static RequestHandle doGet(String relativeUrl, RequestParams params, ResponseHandler responseHandler){

        params=addCommonParams(params);
        //完整url
        String url=getAbsolutUrl(relativeUrl);
        //在此处添加网络监测
        if (NetUtil.hasNetwork(App.getInstance())){
            return ASYNC_CLIENT.get(null,url,getCommonHeader(),params,responseHandler);
        }else{
            Log.v(TAG, ">> 网络连接异常");
            onNetDisabled(responseHandler);
            return null;
        }

    }


    public static RequestHandle doPost(String relativeUrl, RequestParams params, ResponseHandler responseHandler){
        params=addCommonParams(params);
        String url=getAbsolutUrl(relativeUrl);
        if (NetUtil.hasNetwork(App.getInstance())){
            return ASYNC_CLIENT.post(null,url,getCommonHeader(),params,CONTENT_TYPE_FORM,responseHandler);
        }else{
            Log.v(TAG, ">> 网络连接异常");
            onNetDisabled(responseHandler);
            return null;
        }

    }


    public static Header[] getCommonHeader(){
        Header[] headers = new BasicHeader[1];
        headers[0]=new BasicHeader("token", ShareUtil.getInstance().getString("token",""));
        return headers;
    }

    //无网络
    private static void onNetDisabled(ResponseHandler responseHandler) {
        responseHandler.onStart();
        responseHandler.onFailure(0, -1, "网络连接异常");
        responseHandler.onFinish();
    }

    //完整url路径
    private static String getAbsolutUrl(String relativeUrl){
        if (TextUtils.isEmpty(relativeUrl)) {
            return ServerInfo.getServerAddress();
        }
        return ServerInfo.getServerAddress() + relativeUrl;
    }

    //参数
    private static RequestParams addCommonParams(RequestParams params) {
        if (params == null) {
            params = new RequestParams();
        }
        return params;
    }


}
