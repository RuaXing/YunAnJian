package com.quanta.aj.yunanjian.helper;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import com.quanta.aj.yunanjian.orm.AppUser;
import com.quanta.aj.yunanjian.util.JsonUtils;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 许德建 on 2014/8/8.
 */
public class HttpHelper {

    /**
     * 请求数据
     *
     * @param ctx
     * @param uri
     * @param params
     * @param handler
     */
    public static void post(Context ctx, String uri, RequestParams params, AsyncHttpResponseHandler handler) {
        post(ctx,uri,params,-1,handler);
    }

    /**
     * 请求数据
     *
     * @param ctx
     * @param uri
     * @param params
     * @param timeout
     * @param handler
     */
    public static void post(Context ctx, String uri, RequestParams params, int timeout, AsyncHttpResponseHandler handler) {
        AsyncHttpClient client = new AsyncHttpClient();
        if(timeout > 0) client.setTimeout(timeout);
        String url = MyApplication.getUrl(ctx, uri);
        if (params == null) params = new RequestParams();
        AppUser user = ((MyApplication) ctx.getApplicationContext()).remoteUser;
        if (user != null) params.add("signature", JsonUtils.toString(user));
        Log.i("URL", "post1: "+url);
        client.post(ctx, url, params, handler);
    }

    public static byte[] post(Context ctx, String uri, List<NameValuePair> params) {
        String url = MyApplication.getUrl(ctx, uri);
        Log.i("URL", "post2: "+url);
        if (params == null) params = new ArrayList<NameValuePair>();
        AppUser user = ((MyApplication) ctx.getApplicationContext()).remoteUser;
        if (user != null) params.add(new BasicNameValuePair("signature", JsonUtils.toString(user)));
        InputStream in = null;
        ByteArrayOutputStream out = null;
        byte[] data = null;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost();
            post.setURI(new URI(url));
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params);
            post.setEntity(formEntity);
            HttpResponse response = client.execute(post);
            in = response.getEntity().getContent();
            int len = 0;
            byte[] bs = new byte[1024];
            out = new ByteArrayOutputStream();
            while ((len = in.read(bs)) > 0) {
                out.write(bs, 0, len);
            }
            data = out.toByteArray();
        } catch (Exception e) {
            Log.e(HttpHelper.class.getName(),"同步请求出错",e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
        return data;
    }
}
