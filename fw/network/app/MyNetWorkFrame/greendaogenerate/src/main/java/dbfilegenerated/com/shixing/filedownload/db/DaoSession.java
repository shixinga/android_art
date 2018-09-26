package com.shixing.filedownload.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.shixing.filedownload.db.DownloadEntity;

import com.shixing.filedownload.db.DownloadEntityDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig downloadEntityDaoConfig;

    private final DownloadEntityDao downloadEntityDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        downloadEntityDaoConfig = daoConfigMap.get(DownloadEntityDao.class).clone();
        downloadEntityDaoConfig.initIdentityScope(type);

        downloadEntityDao = new DownloadEntityDao(downloadEntityDaoConfig, this);

        registerDao(DownloadEntity.class, downloadEntityDao);
    }
    
    public void clear() {
        downloadEntityDaoConfig.getIdentityScope().clear();
    }

    public DownloadEntityDao getDownloadEntityDao() {
        return downloadEntityDao;
    }

}