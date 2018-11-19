package com.mvp.baselibrary.net

import okhttp3.*

/**
 * 签名加密拦截
 *
 * @auth wxf on 2018/11/14.
 */
class SignInterceptor : Interceptor{

    override fun intercept(chain: Interceptor.Chain): Response {
        // 获取request
        var request = chain.request()

        // 如果设置了header,就不再设置了
        if(null != request.header("X-Req-Timestamp")
                && request.header("X-Req-Timestamp")!!.isNotEmpty()){
            return chain.proceed(request)
        }

        // 初始化参数map
        var paramMap : Map<String,String>? = null
        // 根据get和post请求分别获取参数
        if(request.method() == "GET"){
            paramMap = getParamForGET(request)
        } else if(request.method() == "POST"){
            paramMap = getParamForPOST(request)
        }

//        // 设置header
//        request = request
//                .newBuilder()
//                .addHeader("X-Req-Timestamp", nowTime)
//                .addHeader("X-Req-Auth", sign)
//                .build();
        return chain.proceed(request)
    }

    /**
     * 获取get方法中的参数
     *
     * @param request
     *          OKHttp request
     *
     * @return 参数
     */
    private fun getParamForGET(request : Request):Map<String,String>{
        // 直接返回getParamMap
        return getParamMapFromUrl(request)
    }

    /**
     * 获取Post方法中的参数
     *
     * @param request
     *          OKHttp request
     *
     * @return 参数
     */
    private fun getParamForPOST(request : Request):Map<String,String>{
        // 初始化参数
        val params = mutableMapOf<String,String>()
        // 根据条件判断
        when {
            request.body() is FormBody -> {
                // 获取FormBody
                val formBody = request.body() as FormBody
                // 遍历循环
                for (i in 0 until formBody.size()) {
                    // 放入要返回的参数中map中
                    params[formBody.encodedName(i)] = formBody.encodedValue(i)
                }
            }
            request.body() is MultipartBody -> {
                // 上传文件格式，需要特定
            }
            else -> {
                // 直接用URL的方式获取
                params.putAll(getParamMapFromUrl(request))
            }
        }

        return params
    }

    /**
     * 根据URL获取参数
     *
     * @param request
     *          OKHttp request
     *
     * @return 参数
     */
    private fun getParamMapFromUrl(request : Request) : Map<String,String>{
        // 初始化parameterNames的list
        val parameterNames = mutableListOf<String>()
        // 获取所有参数名
        parameterNames.addAll( request.url().queryParameterNames())
        // 初始化返回的参数
        val params = mutableMapOf<String,String>()

        // 遍历所有参数名
        for (i in 0 until parameterNames.size) {
            // 根据参数名获取值
            val value = if ( request.url().queryParameterValues(parameterNames[i]) != null &&  request.url().queryParameterValues(parameterNames[i]).size > 0) {
                // 有value的情况，设置value
                request.url().queryParameterValues(parameterNames[i])[0]
            } else {
                // 其他情况，设置""
                ""
            }
            // 放入返回的参数map
            params[parameterNames[i]] = value
        }

        return params
    }
}