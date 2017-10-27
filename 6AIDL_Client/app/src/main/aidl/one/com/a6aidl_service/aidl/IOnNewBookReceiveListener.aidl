// IOnNewBookReceiveListener.aidl
package one.com.a6aidl_service.aidl;

// Declare any non-default types here with import statements
import one.com.a6aidl_service.aidl.Book;
interface IOnNewBookReceiveListener {
   void onNewBookReceive(in Book book);
}
