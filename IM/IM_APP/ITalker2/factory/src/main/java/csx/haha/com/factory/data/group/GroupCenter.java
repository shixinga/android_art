package csx.haha.com.factory.data.group;

import csx.haha.com.factory.model.card.GroupCard;
import csx.haha.com.factory.model.card.GroupMemberCard;

/**
 * 群中心的接口定义
 *
 */
public interface GroupCenter {
    // 群卡片的处理
    void dispatch(GroupCard... cards);

    // 群成员的处理
    void dispatch(GroupMemberCard... cards);
}