package com.example.a75213.myapplication.chapter02.aidi;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.a75213.myapplication.Book;
import com.example.a75213.myapplication.IBookManager;
import com.example.a75213.myapplication.IOnNewBookArrivedListener;
import com.example.a75213.myapplication.R;
import com.example.a75213.myapplication.chapter02.content_provider.ProviderActivity;

import java.util.List;

public class AIDIBookActivity extends AppCompatActivity {
    private static final String TAG = AIDIBookActivity.class.getSimpleName();
    private static final int MEESSAGE_NEW_BOOK_ARRIVED = 1;

    private  IBookManager mRemoteBookManager ;


    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
           switch (msg.what){
               case MEESSAGE_NEW_BOOK_ARRIVED:
                   Log.e(TAG , "receive new book : " + ((Book)msg.obj).bookName);
                   break;
               default:
                   super.handleMessage(msg);
           }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidibook);
        Intent intent = new Intent(this , MessengerAIDIService.class);
        bindService(intent , mConnection , Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
           IBookManager bookManager = IBookManager.Stub.asInterface(service);
            mRemoteBookManager = bookManager;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public void onServiceDisconnected(ComponentName className){
        mRemoteBookManager = null;
        Log.e(TAG , "binder died.");
    }

    private IOnNewBookArrivedListener mOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book book) throws RemoteException {
            mHandler.obtainMessage(MEESSAGE_NEW_BOOK_ARRIVED , book).sendToTarget();
        }
    };

    public void sendAIDI(View view){
        try {
            mRemoteBookManager.registerLister(mOnNewBookArrivedListener);
            mRemoteBookManager.addBook(new Book(666,"少妇白洁"));
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<Book> list = null;
                    try {
                        list = mRemoteBookManager.getBookList();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    for (int i = 0 ; i < list.size() ; i++){
                        Book book = list.get(i);
                        Log.e(TAG , "查询的书" + "\n书编号:"+book.bookId + " 书名:" + book.bookName);
                    }
                }
            }).start();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(this , ProviderActivity.class));
    }

    @Override
    protected void onDestroy() {
        if (mRemoteBookManager != null && mRemoteBookManager.asBinder().isBinderAlive()){
            try {
                Log.e(TAG , "unregister listener:" + mOnNewBookArrivedListener);
                mRemoteBookManager.unregisterLister(mOnNewBookArrivedListener);
            }catch (RemoteException e){
                e.printStackTrace();
            }
        }
        Thread.currentThread().interrupt();
        unbindService(mConnection);
        super.onDestroy();
    }
}
