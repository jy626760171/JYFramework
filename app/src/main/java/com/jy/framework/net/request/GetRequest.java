package com.jy.framework.net.request;

import java.util.Map;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

/**
 * Created by zhy on 15/12/14.
 */
public class GetRequest extends OkHttpRequest {
	public GetRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers) {
		super(url, tag, params, headers);
	}

	@Override
	protected RequestBody buildRequestBody() {
		return null;
	}

	@Override
	protected Request buildRequest(Request.Builder builder, RequestBody requestBody) {
		return builder.get().build();
	}

}
