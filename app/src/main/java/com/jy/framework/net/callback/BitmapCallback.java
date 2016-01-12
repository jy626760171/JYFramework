package com.jy.framework.net.callback;

import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.squareup.okhttp.Response;

/**
 * Created by zhy on 15/12/14.
 */
public abstract class BitmapCallback extends Callback<Bitmap> {
	@Override
	public Bitmap parseNetworkResponse(Response response) throws IOException {
		return BitmapFactory.decodeStream(response.body().byteStream());
	}

}
