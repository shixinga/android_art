package one.com.bindler3;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shixing on 2017/4/26.
 */

public class Book implements Parcelable {
    public int bookId;
    public String userName;

    public Book() {

    }

    public Book(int bookId, String userName) {
        this.bookId = bookId;
        this.userName = userName;
    }

    protected Book(Parcel in) {
        bookId = in.readInt();
        userName = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(bookId);
        parcel.writeString(userName);
    }
}
