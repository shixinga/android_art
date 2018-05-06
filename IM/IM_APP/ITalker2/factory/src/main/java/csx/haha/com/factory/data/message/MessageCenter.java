package csx.haha.com.factory.data.message;

import csx.haha.com.factory.model.card.MessageCard;

/**
 * Created by sunray on 2018-5-3.
 */

public interface MessageCenter {
    void dispatch(MessageCard... cards);
}
