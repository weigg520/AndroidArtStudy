package com.example.a75213.myapplication.chapter02.aidi;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.a75213.myapplication.Book;
import com.example.a75213.myapplication.IBookManager;
import com.example.a75213.myapplication.IOnNewBookArrivedListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by 75213 on 2017/12/14.
 */

public class MessengerAIDIService extends Service {
    private static final String TAG = MessengerAIDIService.class.getSimpleName();
    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1 , "第一行代码"));
        mBookList.add(new Book(2 , "Android开发艺术探索"));
        new Thread(new ServiceWorker()).start();
    }
    private AtomicBoolean mIsServiceDestroy= new AtomicBoolean(false);
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<Book>();
    private RemoteCallbackList<IOnNewBookArrivedListener> mListenersList = new RemoteCallbackList<IOnNewBookArrivedListener>();

    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            SystemClock.sleep(5000);
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void registerLister(IOnNewBookArrivedListener listener) throws RemoteException {
            mListenersList.register(listener);
            Log.e(TAG , "registerListener , current size:" + mListenersList.beginBroadcast());
            mListenersList.finishBroadcast();
        }

        @Override
        public void unregisterLister(IOnNewBookArrivedListener listener) throws RemoteException {
            mListenersList.unregister(listener);
            Log.e(TAG , "registerListener , current size:" + mListenersList.beginBroadcast());
            mListenersList.finishBroadcast();
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private void onNewBookArrived(Book book) throws RemoteException{
        mBookList.add(book);
        final int N = mListenersList.beginBroadcast();
        for (int i = 0 ;i < N ; i++){
            IOnNewBookArrivedListener listener = mListenersList.getBroadcastItem(i);
            if (listener != null){
                listener.onNewBookArrived(book);
            }
        }
        mListenersList.finishBroadcast();
    }

    private class ServiceWorker implements Runnable{
        @Override
        public void run() {
            while (!mIsServiceDestroy.get()){
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int bookId = mBookList.size() + 1;
                Book newbook =  new Book(bookId , "new Book#" + bookId);
                try {
                    onNewBookArrived(newbook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        mIsServiceDestroy.set(true);
        super.onDestroy();
    }
}
