package com.andrenas.opencv_tutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    private ViewHolder mViewHolder = new ViewHolder();
    private static final int MY_REQUEST_CODE_PERMISSIONS = 777;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        verifyPermission();

        this.mViewHolder.mButton = this.findViewById(R.id.btn_camera);
        this.mViewHolder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchCamera();
            }
        });

        //OnRequestResult
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == MY_REQUEST_CODE_PERMISSIONS){
            if ((grantResults.length >0) && (grantResults[0]) + (grantResults[1]) == PackageManager.PERMISSION_GRANTED ) {
                //permission Are Granted
                //dispatchCamera();
            }else{
                //Permission Denied
            }
        }

    }

    public void verifyPermission(){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1){
            if(ContextCompat.checkSelfPermission(MenuActivity.this, Manifest.permission.CAMERA) +
                    ContextCompat.checkSelfPermission(MenuActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                //When Permission Granted
                if(ActivityCompat.shouldShowRequestPermissionRationale(MenuActivity.this, Manifest.permission.CAMERA) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(MenuActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                ){
                    //create alert dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
                    builder.setTitle("Granted Those Permisson");
                    builder.setMessage("Camera and Write Storage");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MenuActivity.this, new String[]{
                                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
                            }, MY_REQUEST_CODE_PERMISSIONS);
                        }
                    });
                    builder.setNegativeButton("CANCEL", null);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }else{
                    ActivityCompat.requestPermissions(MenuActivity.this, new String[]{
                            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, MY_REQUEST_CODE_PERMISSIONS);
                }

            }//

        }// verify version
    }

    public void dispatchCamera(){
        Intent intent = new Intent(MenuActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private static class ViewHolder{
        Button mButton;
    }
}
