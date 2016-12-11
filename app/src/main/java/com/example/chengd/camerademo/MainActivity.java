package com.example.chengd.camerademo;

import android.graphics.Camera;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {
private ImageView imageview;
    private File file;
    private android.hardware.Camera camera;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SurfaceView mSurfaceView = (SurfaceView) this.findViewById(R.id.surfaceView1);
        SurfaceHolder mSurfaceHolder=mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }
public void takePhoto(View v){
    camera.takePicture(null,null,pictureCallback);
}
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
          camera = android.hardware.Camera.open();
        android.hardware.Camera.Parameters params=camera.getParameters();
        params.setFocusMode(android.hardware.Camera.Parameters.FOCUS_MODE_AUTO);
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        try{
            camera.setPreviewDisplay(holder);
        }catch (IOException exception){
            camera.release();
            camera=null;
        }
        camera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }
        android.hardware.Camera.PictureCallback pictureCallback = new android.hardware.Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, android.hardware.Camera camera) {
                if(data!=null){
                    savePicture(data);
                }
            }
        };
        public void savePicture(byte[] data){
        try{
            String imageId = System.currentTimeMillis()+"";
            String pathName = Environment.getExternalStorageDirectory().getPath()+"/";
            File file = new File(pathName);
            if(!file.exists()){
                file.mkdir();
            }
            pathName+=imageId+".jpeg";
            file=new File(pathName);
            if(!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data);
            fos.close();
            Toast.makeText(this,"已保存在"+pathName,Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    }

