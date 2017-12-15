package com.example.a75213.myapplication.chapter02.content_provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.a75213.myapplication.chapter02.content_provider.dao.DbOpenHelper;


/**
 * Created by 75213 on 2017/12/15.
 */

public class BookProvider extends ContentProvider {
    private static final String TAG = BookProvider.class.getSimpleName();
    public static final String AUTHORITY = "com.example.a75213.myapplication.chapter02.content_provider";
    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/book");
    public static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/user");

    public static final int BOOK_URI_CODE = 0;
    public static final int USER_URI_CODE = 1;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private Context mContext;
    private SQLiteDatabase mDb;

    static {
        sUriMatcher.addURI(AUTHORITY , "book" , BOOK_URI_CODE);
        sUriMatcher.addURI(AUTHORITY , "user" , USER_URI_CODE);
    }


    private String getTableName(Uri uri){
        String tableName = null;
        switch (sUriMatcher.match(uri)){
            case BOOK_URI_CODE:
                tableName = DbOpenHelper.BOOK_TABLE_NAME;
                break;
            case USER_URI_CODE:
                tableName = DbOpenHelper.USER_TABLE_NAME;
                break;
            default:
                break;
        }
        return tableName;
    }

    @Override
    public boolean onCreate() {
        Log.e(TAG , "onCreate ,current thread:" + Thread.currentThread().getName());
        mContext = getContext();
        initProviderData();
        return false;
    }

    private void initProviderData() {
        mDb = new DbOpenHelper(mContext).getWritableDatabase();
        mDb.execSQL("delete from " + DbOpenHelper.BOOK_TABLE_NAME);
        mDb.execSQL("delete from " + DbOpenHelper.USER_TABLE_NAME);
        mDb.execSQL("insert into book values(3 , 'Android1');");
        mDb.execSQL("insert into book values(4 , 'Android2');");
        mDb.execSQL("insert into book values(5 , 'Android3');");
        mDb.execSQL("insert into user values(6 , 'Android4',1);");
        mDb.execSQL("insert into user values(7 , 'Android5',0);");
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.e(TAG , "query ,current thread:" + Thread.currentThread().getName());
        String table = getTableName(uri);
        if (table == null){
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        return mDb.query(table , projection , selection , selectionArgs , null , null , sortOrder , null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.e(TAG , "getType");
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.e(TAG , "insert");
        String table = getTableName(uri);
        if (table == null){
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        mDb.insert(table , null , values);
        mContext.getContentResolver().notifyChange(uri , null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.e(TAG , "delete");
        String table = getTableName(uri);
        if (table == null){
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int count = mDb.delete(table , selection , selectionArgs);
        if (count > 0){
            mContext.getContentResolver().notifyChange(uri , null);
        }
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.e(TAG , "update");
        String table = getTableName(uri);
        if (table == null){
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int row = mDb.update(table,values , selection , selectionArgs);
        if (row > 0){
            mContext.getContentResolver().notifyChange(uri , null);
        }
        return row;
    }
}
