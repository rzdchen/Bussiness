package net.cc.common.okhttp.interceptors;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.RawRes;

import net.cc.common.utils.FileUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by cc on 2017/4/11
 */

public class DebugInterceptor extends BaseInterceptor {

    private final String DEBUG_URL;
    private final int DEBUG_RAW_ID;
    private Context context;

    public DebugInterceptor(String debugUrl, int rawId, Context context) {
        this.DEBUG_URL = debugUrl;
        this.DEBUG_RAW_ID = rawId;
        this.context = context;
    }

    private Response getResponse(Interceptor.Chain chain, String json) {
        return new Response.Builder()
                .code(200)
                .addHeader("Content-Type", "application/json")
                .body(ResponseBody.create(MediaType.parse("application/json"), json))
                .message("OK")
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .build();
    }

    private Response debugResponse(Interceptor.Chain chain, @RawRes int rawId) {
        final String json = FileUtil.getRawFile(context, rawId);
        return getResponse(chain, json);
    }

    @Override
    public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
        final String url = chain.request().url().toString();
        if (url.contains(DEBUG_URL)) {
            return debugResponse(chain, DEBUG_RAW_ID);
        }
        return chain.proceed(chain.request());
    }
}
