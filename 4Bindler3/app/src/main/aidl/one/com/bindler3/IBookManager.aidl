// IBookManager.aidl
package one.com.bindler3;

// Declare any non-default types here with import statements
import one.com.bindler3.Book;
interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
}
