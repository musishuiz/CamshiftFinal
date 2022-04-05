package com.example.camshift;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.*;
import android.os.Bundle;
import android.view.View;

import java.io.FileNotFoundException;


public class MainActivity extends AppCompatActivity {


    private static final int ALBUM_REQUEST_CODE =111 ;
    private static final int REQUEST_CODE_PICK_IMAGE = 111;
    private static final int REQUEST_CODE_CAPTURE_CAMEIA = 1;
    public Button cameraBtn,videoBtn;
    public ImageView photoTv;
    public VideoView videoView;
    public View view;
    public Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    //初始化
    private void initViews(){
        photoTv=findViewById(R.id.imageView);
        videoView=findViewById(R.id.videoView);
        cameraBtn = findViewById(R.id.button_camera);
        videoBtn = findViewById(R.id.button_video);
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                startCamera(view);
            }
        });
        videoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocalVideo();
            }
        });

    }

    //实时跟踪，需要启动摄像头
    public void startCamera(View view){
        intent=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivity(intent);;
    }

    //获取本地视频
    private void getLocalVideo() {
        intent = new Intent(Intent.ACTION_PICK);
        intent.setType("video/*");//类型
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    //本地视频获取后展示
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_PICK_IMAGE) {
            Uri uri = data.getData(); //to do find the path of pic
            String img_url=uri.getPath();
            ContentResolver cr = this.getContentResolver();
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            final VideoView videoView = (VideoView) findViewById(R.id.videoView);
            /* 将Bitmap设定到ImageView */
            videoView.setVideoURI(uri);
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    videoView.start();
                }
            });
        } else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA) {
            Uri uri = data.getData(); //to do find the path of pic } }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}