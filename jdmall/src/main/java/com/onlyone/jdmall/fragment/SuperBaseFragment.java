package com.onlyone.jdmall.fragment;

import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.onlyone.jdmall.pager.LoadListener;
import com.onlyone.jdmall.utils.NetUtil;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment
 * @创建者: lianjiacheng
 * @创建时间: 2016/3/5 15:34
 * @描述: ${TODO}
 */
public abstract class SuperBaseFragment<T> extends BaseFragment<T> {


    /**
     * 设置请求方法
     * Request.Method.GET/Request.Method.POST
     */
    public int getMethod(){
        return Request.Method.GET;
    }

    /**
     * 设置请求的url,
     * 只调用此方法默认Method是get请求,如果需要其他请求方式,请覆getMethod方法
     * <p>不知道具体实现,但是又必须覆写,定义为抽象方法,交给子类具体实现
     * @return
     */
    protected abstract String getUrl();

    @Override
    protected void loadData(final LoadListener<T> listener) {
        RequestQueue queue = NetUtil.getRequestQueue();
        Response.Listener<String> success = new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonStr) {
                T t = parseJson(jsonStr);
                listener.onSuccess(t);
            }
        };
        Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        };
        StringRequest request = new StringRequest(getMethod(), getUrl(), success, error);
        queue.add(request);

    }

    /**
     * 处理异常的方法
     * <p>不知道具体实现,但是又必须覆写,定义为抽象方法,交给子类具体实现
     * @param e 异常
     */
    @Override
    protected abstract void handleError(Exception e);

    /**
     * 解析json方法.
     * <p>不知道具体实现,但是又必须覆写,定义为抽象方法,交给子类具体实现
     * @param jsonStr
     * @return
     */
    protected abstract T parseJson(String jsonStr);

    /**
     * 加载成功的视图
     * <p>不知道具体实现,但是又必须覆写,定义为抽象方法,交给子类具体实现
     * @return
     */
    @Override
    protected abstract View loadSuccessView();

    /**
     * 加载数据完成,刷新UI,覆写这个方法的人,需要判断data是否为null
     * @param data 数据
     */
    @Override
    protected abstract void refreshSuccessView(T data);


}