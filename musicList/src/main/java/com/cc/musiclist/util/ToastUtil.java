/**
 *
 */
package com.cc.musiclist.util;

import android.content.Context;
import android.widget.Toast;

import com.cc.musiclist.application.BaseApplication;


public class ToastUtil {

    public static void show(Context context, String info) {
        Toast.makeText(context, info, Toast.LENGTH_LONG).show();
    }

    public static void show(Context context, int info) {
        Toast.makeText(context, info, Toast.LENGTH_LONG).show();
    }

    private static Toast toast;
    private static Context context = BaseApplication.context;

    /**
     * 显示吐司 后出现的吐司立即取代之前的
     *
     * @param msg  消息
     * @param time 时间 0为短时间 否则为长时间
     */
    public static void showToast(String msg, int time) {
        if (null != toast)
            toast.cancel();

        if (time == 0)
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        else
            toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);

        toast.show();
    }

    /**
     * 显示吐司 后出现的吐司立即取代之前的
     *
     * @param msg  消息字符资源
     * @param time 时间 0为短时间 否则为长时间
     */
    public static void showToast(int msg, int time) {
        if (null != toast)
            toast.cancel();

        if (time == 0)
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        else
            toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);

        toast.show();
    }

    public static void showerror(Context context, int rCode) {
       /* try {
	        switch (rCode) {
	        case 13:
	            throw new AMapException(AMapException.ERROR_USERID);	        
            case 15:
                throw new AMapException(AMapException.ERROR_UPLOADAUTO_STARTED);
	        case 16:
	            throw new AMapException(AMapException.ERROR_BINDER_KEY);
	        case 21:
                throw new AMapException(AMapException.ERROR_IO);
	        case 22:
                throw new AMapException(AMapException.ERROR_SOCKET);
	        case 23:
                throw new AMapException(AMapException.ERROR_SOCKE_TIME_OUT);
	        case 24:
                throw new AMapException(AMapException.ERROR_INVALID_PARAMETER);
	        case 25:
                throw new AMapException(AMapException.ERROR_NULL_PARAMETER);
	        case 26:
                throw new AMapException(AMapException.ERROR_URL);
	        case 27:
                throw new AMapException(AMapException.ERROR_UNKNOW_HOST);
	        case 28:
                throw new AMapException(AMapException.ERROR_UNKNOW_SERVICE);
	        case 29:
                throw new AMapException(AMapException.ERROR_PROTOCOL);
	        case 30:
                throw new AMapException(AMapException.ERROR_CONNECTION);
	        case 31:
                throw new AMapException(AMapException.ERROR_UNKNOWN);
	        case 32:
                throw new AMapException(AMapException.ERROR_FAILURE_AUTH);
	        case 33:
	            throw new AMapException(AMapException.ERROR_SERVICE);
	        case 34:
	            throw new AMapException(AMapException.ERROR_SERVER);
	        case 35:
                throw new AMapException(AMapException.ERROR_QUOTA);
	        case 36:
                throw new AMapException(AMapException.ERROR_REQUEST);
	        case 37:
                throw new AMapException(AMapException.ERROR_SHARE_SEARCH_FAILURE);
	        case 1901:
                throw new AMapException(AMapException.AMAP_LICENSE_IS_EXPIRED);
	        case 39:
                throw new AMapException(AMapException.ERROR_USERKEY_PLAT_NOMATCH);
	        case 1001:
                throw new AMapException(AMapException.AMAP_SIGNATURE_ERROR);
	        case 43:
                throw new AMapException(AMapException.ERROR_ROUTE_FAILURE);
	        case 44:
                throw new AMapException(AMapException.ERROR_OVER_DIRECTION_RANGE);
	        case 45:
                throw new AMapException(AMapException.ERROR_OUT_OF_SERVICE);
	        case 46:
                throw new AMapException(AMapException.ERROR_ID_NOT_FOUND);
	        case 60:
                throw new AMapException(AMapException.ERROR_SCODE);
	        case 2000:
                throw new AMapException(AMapException.AMAP_TABLEID_NOT_EXIST );
	        case 2001:
                throw new AMapException(AMapException.AMAP_ID_NOT_EXIST);
	        case 11:
	            Toast.makeText(context, "两次单次上传的间隔低于 5 秒", Toast.LENGTH_LONG).show();
	            break;
	        case 12:
	            Toast.makeText(context, "Uploadinfo 对象为空", Toast.LENGTH_LONG).show();
                break;
	        case 14:
	            Toast.makeText(context, "Point 为空，或与前次上传的相同", Toast.LENGTH_LONG).show();
                break;    
	        }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }*/
    }
}
