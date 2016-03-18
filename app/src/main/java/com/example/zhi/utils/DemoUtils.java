package com.example.zhi.utils;

/**
 * Author：Mark
 * Date：2015/12/8 0008
 * Tell：15006330640
 */
public class DemoUtils {

//    public static Login_Object getLoginAccount(Context context) {
//        String yunAccount = context.getSharedPreferences(ConstantString.USER, Activity.MODE_PRIVATE).getString(ConstantString.USERNAME, "");
//        Login_Object object = new Login_Object();
//        object.setUsername(yunAccount);
//        object.setPassword(context.getSharedPreferences(ConstantString.USER, Activity.MODE_PRIVATE).getString(ConstantString.PASSWORD,""));
//        return object;
//    }

    public static void dismissPostingDialog( ECProgressDialog progressDialog) {
        if(progressDialog == null || !progressDialog.isShowing()) {
            return ;
        }
        progressDialog.dismiss();
        progressDialog = null;
    }
}
