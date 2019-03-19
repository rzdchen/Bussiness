package net.cc.common.okhttp;

import net.cc.common.module.AdInstance;
import net.cc.common.okhttp.listener.DisposeDataHandle;
import net.cc.common.okhttp.listener.DisposeDataListener;
import net.cc.common.okhttp.request.CommonRequest;

/**
 * Created by renzhiqiang on 16/10/27.
 *
 * @function sdk请求发送中心
 */
public class RequestCenter {

    /**
     * 发送广告请求
     */
    public static void sendImageAdRequest(String url, DisposeDataListener listener) {

        CommonOkHttpClient.post(CommonRequest.createPostRequest(url, null),
                new DisposeDataHandle(listener, AdInstance.class));
    }
}
