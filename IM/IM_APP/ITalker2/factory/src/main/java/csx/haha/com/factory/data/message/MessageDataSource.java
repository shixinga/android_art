package csx.haha.com.factory.data.message;


import csx.haha.com.factory.data.DbDataSource;
import csx.haha.com.factory.model.db.Message;

/**
 * 消息的数据源定义，他的实现是：MessageRepository, MessageGroupRepository
 * 关注的对象是Message表
 *
 */
public interface MessageDataSource extends DbDataSource<Message> {
}
