package com.example.comitchat.utility;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.comitchat.R;

public class PermissionUtil {
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
      /*  BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;

        mBitmapInsurance = BitmapFactory.decodeFile(mCurrentPhotoPath,options);*/

    public PermissionUtil(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.app_name), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void updatePermissionPreferences(String permission){
        switch (permission){
            case "camera":
//                editor.putBoolean(context.getString(R.string.permission_camera),true);
                editor.commit();
                break;
            case "storage":
//                editor.putBoolean(context.getString(R.string.permission_storage),true);
                editor.commit();
                break;
            case "contacts":
                editor.putBoolean(context.getString(R.string.permission_contacts),true);
                editor.commit();
                break;
        }
    }

    public boolean checkPermissionPreferences(String permission){
        boolean isShown = false;
        switch (permission){
            case "camera":
//                isShown = sharedPreferences.getBoolean(context.getString(R.string.permission_camera),false);
                break;
            case "storage":
//                isShown = sharedPreferences.getBoolean(context.getString(R.string.permission_storage),false);
                break;
            case "contacts":

                isShown = sharedPreferences.getBoolean(context.getString(R.string.permission_contacts),false);
                break;
        }
        return isShown;
    }

}
