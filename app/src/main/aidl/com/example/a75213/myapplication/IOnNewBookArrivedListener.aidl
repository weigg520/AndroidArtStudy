// IOnNewBookArrivedListener.aidl
package com.example.a75213.myapplication;

// Declare any non-default types here with import statements
import com.example.a75213.myapplication.Book;

interface IOnNewBookArrivedListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
  void onNewBookArrived(in Book book);
}
