package com.example.user.contentprovider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ListView lvMedia;
    private SimpleCursorAdapter adapter;
    private Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvMedia = findViewById(R.id.lvMedia);
        requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,1368);

    }

    /**
     * Thuc hien xin quyen: quyen doc the nho, quyen su dung camera,....
     * thi tu android 5.0 phai xin quyen runtime va xin quyen os
     */


    //xay dung phuong thuc de xin quyen nguoi dung
    public void requestPermission(String permission,int requestCode){
        //kiem tra nguoi dung da cho phep cap quyen chua
        //neu da cho phep thi se thuc hien cong viec
        if(ContextCompat.checkSelfPermission(this,permission)== PackageManager.PERMISSION_GRANTED){
            //lam cong viec
            readMedia();

        }else {//neu chua cho phep thi xin quyen nguoi dung
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{permission},requestCode);
        }

    }

    //xay dung phuong thuc de lang nghe ket qua cua viec yeu cau cap quyen

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(ContextCompat.checkSelfPermission(this,permissions[0])== PackageManager.PERMISSION_GRANTED){
            //nguoi dung click vao button cho phep
            //lam cong viec
            readMedia();

        }else {//click vao button ko cho phep
            Toast.makeText(this,"Khong duoc phep",Toast.LENGTH_SHORT).show();
        }

    }
    //xay dung phuong thuc de doc bai hat trong sdcard

    private void readMedia(){
        //ten cac cot can lay
        String[] cols = {MediaStore.Audio.Media._ID,
        MediaStore.Audio.Media.TITLE,
        MediaStore.Audio.Media.DURATION};
        int[] ids = {R.id.tvID,R.id.tvTilte,R.id.tvDuration};
        cursor= getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,null);
        if(cursor!=null){
            adapter= new SimpleCursorAdapter(this,R.layout.item_media,cursor,cols,ids);
            lvMedia.setAdapter(adapter);
        }

    }

}
