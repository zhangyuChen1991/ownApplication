package com.sz.china.testmoudule.util;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sz.china.testmoudule.R;


public class DialogUtils {

	public static Dialog createLoadingDialog(Context context, String msg) {  
		  
        LayoutInflater inflater = LayoutInflater.from(context);
        // 得到加载view
        View v = inflater.inflate(R.layout.dialog_loading, null);
        // 加载布局 
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view); 
        ImageView iv_refreshing = (ImageView) v.findViewById(R.id.iv_refreshing);  
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字  
        // 加载旋转动画  
        Animation rotateAnimation = AnimationUtils.loadAnimation(  
                context, R.anim.roate_center_repeat);
        // 使用ImageView显示旋转动画  
        iv_refreshing.startAnimation(rotateAnimation);
        // 设置文本信息  
        if (!TextUtils.isEmpty(msg)) {
        	tipTextView.setText(msg);
		}
  
        // 创建自定义样式dialog
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);  
  
        // 不可以用“返回键”取消  
        loadingDialog.setCancelable(false);
        // 设置布局  
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(  
                LinearLayout.LayoutParams.WRAP_CONTENT,  
                LinearLayout.LayoutParams.WRAP_CONTENT));
        
        return loadingDialog;  

    }
	
}
