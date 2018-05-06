package csx.haha.com.factory.data.user;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import csx.haha.com.factory.data.BaseDbRepository;
import csx.haha.com.factory.data.DataSource;
import csx.haha.com.factory.model.db.User;
import csx.haha.com.factory.model.db.User_Table;
import csx.haha.com.factory.persistence.Account;

/**
 * 联系人仓库
 *
 */
public class ContactRepository extends BaseDbRepository<User> implements ContactDataSource {
    @Override
    public void load(DataSource.SucceedCallback<List<User>> callback) {
        super.load(callback);

        // 加载本地数据库数据
        SQLite.select()
                .from(User.class)
                .where(User_Table.isFollow.eq(true))
                .and(User_Table.id.notEq(Account.getUserId()))
                .orderBy(User_Table.name, true)
                .limit(100)
                .async()
                .queryListResultCallback(this)
                .execute();
    }

    @Override
    protected boolean isRequired(User user) {
        return user.isFollow() && !user.getId().equals(Account.getUserId());
    }
}
