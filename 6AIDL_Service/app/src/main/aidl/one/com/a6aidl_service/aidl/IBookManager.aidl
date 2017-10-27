// IBookManager.aidl
package one.com.a6aidl_service.aidl;

// Declare any non-default types here with import statements
import one.com.a6aidl_service.aidl.Book;
import one.com.a6aidl_service.aidl.IOnNewBookReceiveListener;
interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener(in IOnNewBookReceiveListener listener);
    void unRegisterListener(in IOnNewBookReceiveListener listener);
}
