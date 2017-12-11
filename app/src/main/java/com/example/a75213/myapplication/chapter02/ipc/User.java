package com.example.a75213.myapplication.chapter02.ipc;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 75213 on 2017/12/11.
 */

public class User implements Parcelable {
    private int age;
    private String name;
    private String pass;

    public User(int age , String name , String pass){
        this.age = age;
        this.name = name;
        this.pass = pass;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.age);
        dest.writeString(this.name);
        dest.writeString(this.pass);
    }

    protected User(Parcel in) {
        this.age = in.readInt();
        this.name = in.readString();
        this.pass = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
