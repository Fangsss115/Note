package com.example.fangfang.note;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

/**
 * Created by fangfang on 2017/6/28.
 */

public class SelectAct extends Activity implements OnClickListener{
    private Button s_delete, s_back;
    private ImageView s_img;
    private VideoView s_video;
    private TextView s_tv;
    private NoteDB noteDB;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select);

        s_delete = (Button) findViewById(R.id.s_delete);
        s_back = (Button) findViewById(R.id.s_back);
        s_img = (ImageView) findViewById(R.id.s_img);
        s_video = (VideoView) findViewById(R.id.s_video);
        s_tv = (TextView) findViewById(R.id.s_tv);
        noteDB = new NoteDB(this);
        database = noteDB.getWritableDatabase();

        s_back.setOnClickListener(this);
        s_delete.setOnClickListener(this);

        if (getIntent().getStringExtra(NoteDB.PATH).equals("null")){
            s_img.setVisibility(View.GONE);
        }else {
            s_img.setVisibility(View.VISIBLE);
        }
        if (getIntent().getStringExtra(NoteDB.VIDEO).equals("null")){
            s_video.setVisibility(View.GONE);
        }else {
            s_video.setVisibility(View.VISIBLE);
        }

        //设置需要显示的图片
        s_tv.setText(getIntent().getStringExtra(NoteDB.CONTENT));
        Bitmap bitmap = BitmapFactory.decodeFile(getIntent().getStringExtra(NoteDB.PATH));
        s_img.setImageBitmap(bitmap);
        s_video.setVideoURI(Uri.parse(getIntent().getStringExtra(NoteDB.VIDEO)));
        s_video.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.s_delete:
                deleteDate();
                finish();
                break;
            case R.id.s_back:
                finish();
                break;
        }
    }

    private void deleteDate() {
        database.delete(NoteDB.TABLE_NAME, "id = " + getIntent().getIntExtra(NoteDB.ID, 0), null);
    }
}
