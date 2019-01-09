/*
 * Copyright 2015 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nohttp.sample;

import android.app.Activity;
import android.content.DialogInterface;
import android.widget.Toast;

import com.nohttp.error.NetworkError;
import com.nohttp.error.NotFoundCacheError;
import com.nohttp.error.TimeoutError;
import com.nohttp.error.URLError;
import com.nohttp.error.UnKnownHostError;
import com.nohttp.rest.OnResponseListener;
import com.nohttp.rest.Request;
import com.nohttp.rest.Response;
import com.tool.MyProgressDialog;

import org.json.JSONException;


/**
 * Created in Nov 4, 2015 12:02:55 PM.
 *
 * @author Yan Zhenjie.
 */
public class HttpResponseListener<T> implements OnResponseListener<T> {

    private Activity mActivity;
    /**
     * Dialog.
     */
//    private WaitDialog mWaitDialog;
    /**
     * Request.
     */
    private Request<?> mRequest;
    /**
     * 结果回调.
     */
    private HttpListener<T> callback;

    /**
     * @param activity     context用来实例化dialog.
     * @param request      请求对象.
     * @param httpCallback 回调对象.
     * @param canCancel    是否允许用户取消请求.
     * @param isLoading    是否显示dialog.
     */
    public HttpResponseListener(Activity activity, Request<?> request, HttpListener<T> httpCallback, boolean
            canCancel, boolean isLoading) {
        this.mActivity = activity;
        this.mRequest = request;
        if (activity != null && isLoading) {
//            mWaitDialog = new WaitDialog(activity);
//            mWaitDialog.setCancelable(canCancel);
//            mWaitDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                @Override
//                public void onCancel(DialogInterface dialog) {
//                    dialog.dismiss();
//                    MyProgressDialog.dismiss();
//                    mRequest.cancel();
//                }
//            });
//            mWaitDialog.setOnCancelListener(dialog -> mRequest.cancel());
        }
        this.callback = httpCallback;
    }

    /**
     * 开始请求, 这里显示一个dialog.
     */
    @Override
    public void onStart(int what) {
//        if (mWaitDialog != null && !mActivity.isFinishing() && !mWaitDialog.isShowing()){
//            mWaitDialog.show();
//            MyProgressDialog.show(mActivity,"");
//        }
        String rest=mRequest.url();
        MyProgressDialog.show(mActivity,"");
    }

    /**
     * 结束请求, 这里关闭dialog.
     */
    @Override
    public void onFinish(int what) {
//        if (mWaitDialog != null && mWaitDialog.isShowing()){
//            mWaitDialog.dismiss();
//        }
        MyProgressDialog.dismiss();
    }

    /**
     * 成功回调.
     */
    @Override
    public void onSucceed(int what, Response<T> response) {
        if (callback != null) {
            // 这里判断一下http响应码，这个响应码问下你们的服务端你们的状态有几种，一般是200成功。
            // w3c标准http响应码：http://www.w3school.com.cn/tags/html_ref_httpmessages.asp
            try {
                callback.onSucceed(what, response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 失败回调.
     */
    @Override
    public void onFailed(int what, Response<T> response) {
        Exception exception = response.getException();
        if (exception instanceof NetworkError) {// 网络不好
            Toast.makeText(mActivity, "网络异常",Toast.LENGTH_SHORT).show();
        } else if (exception instanceof TimeoutError) {// 请求超时
            Toast.makeText(mActivity, "请求超时",Toast.LENGTH_SHORT).show();
        } else if (exception instanceof UnKnownHostError) {// 找不到服务器
            Toast.makeText(mActivity, "找不到服务器",Toast.LENGTH_SHORT).show();
        } else if (exception instanceof URLError) {// URL是错的
            Toast.makeText(mActivity, "请求地址错误",Toast.LENGTH_SHORT).show();
        } else if (exception instanceof NotFoundCacheError) {
            // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
//            Toast.makeText(mActivity, "没有找到缓存",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mActivity, "未知错误",Toast.LENGTH_SHORT).show();
        }
//        Logger.e("错误：" + exception.getMessage());
        if (callback != null)
            callback.onFailed(what, response);
    }

}
