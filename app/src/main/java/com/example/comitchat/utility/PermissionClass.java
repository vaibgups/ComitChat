package com.example.comitchat.utility;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.example.comitchat.MainActivity;

import java.util.ArrayList;

public class PermissionClass {
    private Context context;
    private Activity activity;
    private PermissionUtil permissionUtil;

    public PermissionClass(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        permissionUtil = new PermissionUtil(context);
    }



    public void contacts() {
        if (checkPermission(Constant.TXT_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_CONTACTS)) {
                showPermissionExplanation(Constant.TXT_CONTACTS);
            } else if (!permissionUtil.checkPermissionPreferences("contacts")) {
                requestPermission(Constant.TXT_CONTACTS);
                permissionUtil.updatePermissionPreferences("contacts");
            } else {
                Toast.makeText(context, "Please allow contacts permission in your app setting", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                context.startActivity(intent);
            }
        } else {
            Toast.makeText(context, "Contacts permission success", Toast.LENGTH_LONG).show();
        }
    }



    public void camera() {
        if (checkPermission(Constant.TXT_CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
                showPermissionExplanation(Constant.TXT_CAMERA);
            } else if (!permissionUtil.checkPermissionPreferences("camera")) {
                requestPermission(Constant.TXT_CAMERA);
                permissionUtil.updatePermissionPreferences("camera");
            } else {
                Toast.makeText(context, "Please allow camera permission in your app setting", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                context.startActivity(intent);
            }
        } else {
            Toast.makeText(context, "Camera permission success", Toast.LENGTH_LONG).show();
        }
    }



    //   check permission

    private int checkPermission(int permission) {
        int status = PackageManager.PERMISSION_DENIED;
        switch (permission) {
            case Constant.TXT_CAMERA:
                status = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
                break;
            case Constant.TXT_STORAGE:
                status = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                break;
            case Constant.TXT_CONTACTS:
                status = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS);
                break;
        }
        return status;
    }


    //    show Permission Explanation
    private void showPermissionExplanation(final int permission) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (permission == Constant.TXT_CAMERA) {
            builder.setMessage("This app need to access your device camera.. please allow");
            builder.setTitle("Camera Permission Needed..");
        } else if (permission == Constant.TXT_STORAGE) {
            builder.setMessage("This app need to access your device storage.. please allow");
            builder.setTitle("Storage Permission Needed..");
        } else if (permission == Constant.TXT_CONTACTS) {
            builder.setMessage("This app need to access your device contacts.. please allow");
            builder.setTitle("Contacts Permission Needed..");
        }
        builder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (permission == Constant.TXT_CAMERA) {
                    requestPermission(Constant.TXT_CAMERA);
                } else if (permission == Constant.TXT_STORAGE) {
                    requestPermission(Constant.TXT_STORAGE);
                } else if (permission == Constant.TXT_CONTACTS) {
                    requestPermission(Constant.TXT_CONTACTS);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    //    request permission
    private void requestPermission(int permission) {
        switch (permission) {
            case Constant.TXT_CAMERA:
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.CAMERA}, Constant.REQUEST_CAMERA);
                break;
            case Constant.TXT_STORAGE:
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constant.REQUEST_STORAGE);
                break;
            case Constant.TXT_CONTACTS:
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.READ_CONTACTS}, Constant.REQUEST_CONTACTS);
                break;
        }
    }



    public void allPermission() {
        ArrayList<String> permissionsNeeded = new ArrayList<>();
        ArrayList<String> permissionsAvailable = new ArrayList<>();
        permissionsAvailable.add(Manifest.permission.CAMERA);
        permissionsAvailable.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionsAvailable.add(Manifest.permission.READ_CONTACTS);
        for (String permission : permissionsAvailable) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(permission);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestGroupPermission(permissionsNeeded);
        }
    }


    /*********************Request Group Permission************/

    private void requestGroupPermission(ArrayList<String> permission) {
        String[] permissionList = new String[permission.size()];
        permission.toArray(permissionList);
       /* boolean test = true;
        for (String perm : permission){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                test = shouldShowRequestPermissionRationale( perm );
            }
            if (test){
                break;
            }else {
                continue;
            }

        }
//        ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,permission.get(0),REQUEST_CAMERA);

        if (test){
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", this.getPackageName(), null);
            intent.setData(uri);
            this.startActivity(intent);
        }else {
            ActivityCompat.requestPermissions(MainActivity.this, permissionList, REQUEST_GROUP_PERMISSION);
        }*/

        ActivityCompat.requestPermissions(activity, permissionList, Constant.REQUEST_GROUP_PERMISSION);


    }



}
