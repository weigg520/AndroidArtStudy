// IBookManager.aidl
package com.example.a75213.myapplication;

// Declare any non-default types here with import statements
import com.example.a75213.myapplication.Book;
import com.example.a75213.myapplication.IOnNewBookArrivedListener;

interface IBookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    List<Book> getBookList();
    void addBook(in Book book);
    void registerLister(IOnNewBookArrivedListener listener);
    void unregisterLister(IOnNewBookArrivedListener listener);
}
