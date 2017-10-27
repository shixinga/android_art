// IBookManager.aidl
package one.com.myaidl;

// Declare any non-default types here with import statements
import one.com.myaidl.Book;
interface IBookManager {
    void addBook(in Book book);
    List<Book> getBookList();
}
