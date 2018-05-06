package csx.haha.com.factory.presenter.message;

import csx.haha.com.factory.data.helper.UserHelper;
import csx.haha.com.factory.data.message.MessageRepository;
import csx.haha.com.factory.model.db.Message;
import csx.haha.com.factory.model.db.User;

public class ChatUserPresenter extends ChatPresenter<ChatContract.UserView>
        implements ChatContract.Presenter {

    public ChatUserPresenter(ChatContract.UserView view, String receiverId) {
        // 数据源，View，接收者，接收者的类型
        super(new MessageRepository(receiverId), view, receiverId, Message.RECEIVER_TYPE_NONE);


    }

    @Override
    public void start() {
        super.start();

        // 从本地拿这个人的信息
        User receiver = UserHelper.findFromLocal(mReceiverId);
        getView().onInit(receiver);
    }
}
