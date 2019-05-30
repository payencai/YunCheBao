package com.example.yunchebao.net;


/**
 * @ProjectName: UnderPan
 * @Package: com.example.tomsonpan.underpan.utils
 * @ClassName: OnMessageReceived
 * @Description: java类作用描述
 * @Author: Tomson.pan
 * @CreateDate: 2019/4/22 17:09
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/4/22 17:09
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface OnMessageReceived {

    void onSuccess(String response);
    void onError(String error);

}
