package com.andrenas.opencv_tutorial;

/* openCVLibrary348 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.SurfaceView;
import android.webkit.PermissionRequest;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.engine.OpenCVEngineInterface;
import org.opencv.imgproc.Imgproc;

public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    private ViewHolder mViewHolder = new ViewHolder();
    Mat mat1, mat2, mat3;

    BaseLoaderCallback mBaseLoaderCallback;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mViewHolder.cameraView = this.findViewById(R.id._cameraview);
        this.mViewHolder.cameraView.setVisibility(SurfaceView.VISIBLE);
        this.mViewHolder.cameraView.setCvCameraViewListener(this);

        mBaseLoaderCallback = new BaseLoaderCallback(this) {
            @Override
            public void onManagerConnected(int status) {
                switch (status){
                    case BaseLoaderCallback.SUCCESS:
                        mViewHolder.cameraView.enableView();
                        break;
                        default:
                            super.onManagerConnected(status);
                            break;
                }
            }
        };

    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        mat1 = new Mat(width, height, CvType.CV_8UC4);
        mat2 = new Mat(width, height, CvType.CV_8UC4);
        mat3 = new Mat(width, height, CvType.CV_8UC4);


    }

    @Override
    public void onCameraViewStopped() {
        mat1.release();
        mat2.release();
        mat3.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {

        mat1 = inputFrame.rgba();

        Core.transpose(mat1, mat2);
        Imgproc.resize(mat2, mat3, mat3.size(), 0, 0, 0);
        Core.flip(mat3, mat1, 1);

        return mat1;
    }

    /* CICLO */

    @Override
    protected void onResume() {
        super.onResume();
        if(!OpenCVLoader.initDebug()){
            Toast.makeText(this, "ERROR OPENCV CAMERA", Toast.LENGTH_SHORT).show();
        }else{
            mBaseLoaderCallback.onManagerConnected(BaseLoaderCallback.SUCCESS);
        }//!OpenCVLoader.initDebug()
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(this.mViewHolder.cameraView!= null){
            this.mViewHolder.cameraView.disableView();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (this.mViewHolder.cameraView != null){
            this.mViewHolder.cameraView.disableView();
        }else{

        }

    }


    private static class ViewHolder{
        CameraBridgeViewBase cameraView;
    }

}
