package com.sz.china.testmoudule.util;

import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

/**
 * 检查输入判空等 对TextView和Edittext有用
 * 
 * @author zhangyu
 * @date 2016-4-25
 */
public class InputCheckUtil {

	private static final String TAG = "InputCheckUtil";

	/**
	 * 检测输入是否为空
	 * @param tv
	 */
	public static boolean isInputEmpty(TextView tv) {
		if (null != tv) {
			String input = tv.getText().toString();
			if (TextUtils.isEmpty(input))
				return true;
			else
				return false;
		} else {
			Log.e(TAG, "isInputEmpty..  参数textview == null");
			return true;
		}
	}

	/**
	 * 检测两个输入框内容是否一致
	 * 
	 * @param tv1
	 * @param tv2
	 * @return
	 */
	public static boolean inputIsSame(TextView tv1, TextView tv2) {
		String input1 = tv1.getText().toString();
		String input2 = tv2.getText().toString();

		if (!TextUtils.isEmpty(input1) && input1.equals(input2))
			return true;
		else
			return false;
	}

	/**
	 * 批量检测输入是否为空，并作出提示
	 * 
	 * @param strs 与控件相对的提示关键字数组
	 * @param tvs 要检测的输入控件数组 例如：strs = {"旧密码","新密码","确认新密码"} tvs内容对应三个标题所指的输入框
	 * @return 所有输入不为空，返回true 否则返回false
	 */
	public static boolean massiveCheckAndRemind(String[] strs, TextView[] tvs) {
		for (int i = 0; i < tvs.length; i++) {
			if (isInputEmpty(tvs[i])) {
				ToastUtil.showToast(strs[i] + "不能为空", 0);
				return false;
			}
		}

		return true;
	}
}
