package csx.haha.com.factory.data.helper;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import csx.haha.com.factory.model.db.Session;
import csx.haha.com.factory.model.db.Session_Table;

/**
 * Created by sunray on 2018-5-3.
 */

public class SessionHelper {
    // 从本地查询Session
    public static Session findFromLocal(String id) {
        return SQLite.select()
                .from(Session.class)
                .where(Session_Table.id.eq(id))
                .querySingle();
    }
}
