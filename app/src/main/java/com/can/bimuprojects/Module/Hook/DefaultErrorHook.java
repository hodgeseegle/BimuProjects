package com.can.bimuprojects.Module.Hook;

import android.content.Context;

import com.android.volley.VolleyError;
import com.can.bimuprojects.network.beans.ErrorHook;

/**
 * Created by can on 2017/4/12.
 */
public class DefaultErrorHook implements ErrorHook {
    @Override
    public void deal(Context context, VolleyError error) {
    }
}
