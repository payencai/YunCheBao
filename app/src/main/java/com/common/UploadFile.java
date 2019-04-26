package com.common;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.costans.PlatformContans;
import com.google.gson.reflect.TypeToken;
import com.tool.FileUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import top.zibin.luban.Luban;

public class UploadFile {
    private Context context;

    private OnFileUploadListener listener;

    public UploadFile(Context context){
        this.context = context;
    }
    public void upLoadFile(List<Uri> uriList,OnFileUploadListener listener){
        setImages(uriList);
        this.listener = listener;
    }

    private void setImages(List<Uri> uriList) {
        List<File> fileList = new ArrayList<>();
        for (int i = 0;i<uriList.size();i++){
            File fileByUri = FileUtil.getFileByUri(uriList.get(i), context);
            fileList.add(fileByUri);
        }
        new Thread(()-> Flowable.just(fileList)
                .observeOn(Schedulers.io())
                .map(list -> {
                    // 同步方法直接返回压缩后的文件
                    List<File> files = Luban.with(context).load(list).get();
                    if (files.size()>0){
                        uploadImages(files);
                    }
                    return files;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()).start();
    }


    private void uploadImages(List<File> fileList){
        OkHttpClient mOkHttpClent = new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder .setType(MultipartBody.FORM);
        for (int i =0 ;i<fileList.size();i++){
            RequestBody r =  RequestBody.create(MediaType.parse("image/png"), fileList.get(i));
            builder .addFormDataPart("images", "images", r);
        }

        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(PlatformContans.Commom.uploadImgs)
                .post(requestBody)
                .build();
        Call call = mOkHttpClent.newCall(request);
        uploadFileList(call);
    }

    private void upImage(File file) {
        OkHttpClient mOkHttpClent = new OkHttpClient();

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", "image",
                        RequestBody.create(MediaType.parse("image/png"), file));
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(PlatformContans.Commom.uploadImg)
                .post(requestBody)
                .build();
        Call call = mOkHttpClent.newCall(request);
        uploadFileList(call);
    }

    private void uploadFileList( Call call){
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("upload", "onResponse: " + e.getMessage());
                listener.onFileUploadFailed(e.getMessage());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.e("upload", "onResponse: " + string);
                HandlerData.handlerData(string, new TypeToken<BaseModel<String>>() {
                }.getType(), new EndLoadDataType<String>() {
                    @Override
                    public void onFailed() {
                        listener.onFileUploadFailed("未知错误");
                    }

                    @Override
                    public void onSuccess(String s) {
                        if (!TextUtils.isEmpty(s)){
                            listener.onFileUploadSucess(s);
                        }
                    }

                    @Override
                    public void onSuccessBaseModel(BaseModel baseModel) {
                        if (baseModel!=null && baseModel.resultCode!=0){
                            listener.onFileUploadFailed(baseModel.message);
                        }
                    }
                });
            }
        });
    }

    public interface OnFileUploadListener{
        void onFileUploadSucess(String fileUrl);
        void onFileUploadFailed(String message);
    }
}
