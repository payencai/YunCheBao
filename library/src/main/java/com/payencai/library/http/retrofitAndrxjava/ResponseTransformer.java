package com.payencai.library.http.retrofitAndrxjava;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * Created by Zaifeng on 2018/2/28.
 * 对返回的数据进行处理，区分异常的情况。
 */

public class ResponseTransformer {

    public static <T> ObservableTransformer<RetrofitResponse<T>, T> handleResult() {
        return upstream -> upstream
                .onErrorResumeNext(new ErrorResumeFunction<>())
                .flatMap(new ResponseFunction<>());
    }


    /**
     * 非服务器产生的异常，比如本地无无网络请求，Json数据解析错误等等。
     *
     * @param <T>
     */
    private static class ErrorResumeFunction<T> implements Function<Throwable, ObservableSource<? extends RetrofitResponse<T>>> {

        @Override
        public ObservableSource<? extends RetrofitResponse<T>> apply(Throwable throwable) throws Exception {
            return Observable.error(CustomException.handleException(throwable));
        }
    }

    /**
     * 服务其返回的数据解析
     * 正常服务器返回数据和服务器可能返回的exception
     *
     * @param <T>
     */
    private static class ResponseFunction<T> implements Function<RetrofitResponse<T>, ObservableSource<T>> {

        @Override
        public ObservableSource<T> apply(RetrofitResponse<T> tResponse) throws Exception {
            int code = tResponse.getResultCode();
            if (code==0) {
                return Observable.just(tResponse.getData());
            } else {
                return Observable.error(new ApiException(code, tResponse.getMessage()));
            }
        }
    }


}