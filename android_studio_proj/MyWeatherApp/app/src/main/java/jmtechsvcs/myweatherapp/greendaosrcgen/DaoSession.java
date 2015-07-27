package jmtechsvcs.myweatherapp.greendaosrcgen;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {
    //test

    private final DaoConfig cityInfoTableDaoConfig;
    private final DaoConfig cityWeatherCurrCondTableDaoConfig;

    private final CityInfoTableDao cityInfoTableDao;
    private final CityWeatherCurrCondTableDao cityWeatherCurrCondTableDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        cityInfoTableDaoConfig = daoConfigMap.get(CityInfoTableDao.class).clone();
        cityInfoTableDaoConfig.initIdentityScope(type);

        cityWeatherCurrCondTableDaoConfig = daoConfigMap.get(CityWeatherCurrCondTableDao.class).clone();
        cityWeatherCurrCondTableDaoConfig.initIdentityScope(type);

        cityInfoTableDao = new CityInfoTableDao(cityInfoTableDaoConfig, this);
        cityWeatherCurrCondTableDao = new CityWeatherCurrCondTableDao(cityWeatherCurrCondTableDaoConfig, this);

        registerDao(CityInfoTable.class, cityInfoTableDao);
        registerDao(CityWeatherCurrCondTable.class, cityWeatherCurrCondTableDao);
    }
    
    public void clear() {
        cityInfoTableDaoConfig.getIdentityScope().clear();
        cityWeatherCurrCondTableDaoConfig.getIdentityScope().clear();
    }

    public CityInfoTableDao getCityInfoTableDao() {
        return cityInfoTableDao;
    }

    public CityWeatherCurrCondTableDao getCityWeatherCurrCondTableDao() {
        return cityWeatherCurrCondTableDao;
    }

}
