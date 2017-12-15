package com.example.a75213.myapplication.chapter02.content_provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.ConditionVariable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.a75213.myapplication.Book;
import com.example.a75213.myapplication.R;

public class ProviderActivity extends AppCompatActivity {
    private static final String TAG =  ProviderActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
        Uri bookUri = Uri.parse("content://com.example.a75213.myapplication.chapter02.content_provider/book");
        ContentValues values = new ContentValues();
        values.put("_id" , 10);
        values.put("name" ,"程序设计");
        getContentResolver().insert(bookUri,values);
        Cursor bookCursor = getContentResolver().query(bookUri , new String[]{"_id" ,"name"} , null ,null ,null);
        while (bookCursor.moveToNext()){
            Book book = new Book(bookCursor.getInt(0) , bookCursor.getString(1));
            Log.d(TAG , "query book:" + book.toString());
        }
        bookCursor.close();

        Uri userUri = Uri.parse("content://com.example.a75213.myapplication.chapter02.content_provider/user");
        Cursor bookCursor2 = getContentResolver().query(userUri , new String[]{"_id" ,"name" , "sex"} , null ,null ,null);
        while (bookCursor2.moveToNext()){
            Log.d(TAG , "query book:" + bookCursor2.getInt(0) + bookCursor2.getString(1));
        }
        bookCursor2.close();
    }
}
