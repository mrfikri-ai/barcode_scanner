package com.example.newstudent.barcode_v13;

/**
 * Created by new student on 2/6/2017.
 */

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;

@SuppressWarnings("ALL")
public class Show extends Activity{

    ImageView imageView1;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show);
        imageView1 = (ImageView)this.findViewById(R.id.showImage);
        db = this.openOrCreateDatabase("test.db", Context.MODE_PRIVATE, null);
        db.execSQL("create table if not exists tb (a blob)");
    }

    public void saveImage(View view)
    {
        try {
            FileInputStream fis = new FileInputStream("/storage/emulated/cocacola.jpg");
            byte[] image = new byte[fis.available()];
            fis.read(image);

            ContentValues values = new ContentValues();
            values.put("a", image);
            db.insert("tb", null, values);

            fis.close();

            Toast.makeText(this, "insert success", Toast.LENGTH_SHORT).show();
        }
        catch (IOException e)
        {
            //TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void getImage(View view)
    {
        Cursor c = db.rawQuery("select * from tb", null);
        if (c.moveToNext()){
            byte[] image = c.getBlob(0);
            Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
            imageView1.setImageBitmap(bmp);
            Toast.makeText(this, "select success", Toast.LENGTH_SHORT).show();
        }
    }
}
