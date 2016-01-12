package com.jy.framework.net.callback;

import java.io.IOException;

import com.squareup.okhttp.Response;

/**
 * Created by zhy on 15/12/14.
 */
public abstract class StringCallback extends Callback<String> {
	@Override
	public String parseNetworkResponse(Response response) throws IOException {
		return response.body().string();
	}

}
