package jmtechsvcs.myweatherapp.greendaosrcgenpkg;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import jmtechsvcs.myweatherapp.greendaosrcgenpkg.CityInfoTable;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.CityWeatherCurrCondTable;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.WeatherIconTable;

import jmtechsvcs.myweatherapp.greendaosrcgenpkg.CityInfoTableDao;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.CityWeatherCurrCondTableDao;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.WeatherIconTableDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig cityInfoTableDaoConfig;
    private final DaoConfig cityWeatherCurrCondTableDaoConfig;
    private final DaoConfig weatherIconTableDaoConfig;

    private final CityInfoTableDao cityInfoTableDao;
    private final CityWeatherCurrCondTableDao cityWeatherCurrCondTableDao;
    private final WeatherIconTableDao weatherIconTableDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        cityInfoTableDaoConfig = daoConfigMap.get(CityInfoTableDao.class).clone();
        cityInfoTableDaoConfig.initIdentityScope(type);

        cityWeatherCurrCondTableDaoConfig = daoConfigMap.get(CityWeatherCurrCondTableDao.class).clone();
        cityWeatherCurrCondTableDaoConfig.initIdentityScope(type);

        weatherIconTableDaoConfig = daoConfigMap.get(WeatherIconTableDao.class).clone();
        weatherIconTableDaoConfig.initIdentityScope(type);

        cityInfoTableDao = new CityInfoTableDao(cityInfoTableDaoConfig, this);
        cityWeatherCurrCondTableDao = new CityWeatherCurrCondTableDao(cityWeatherCurrCondTableDaoConfig, this);
        weatherIconTableDao = new WeatherIconTableDao(weatherIconTableDaoConfig, this);

        registerDao(CityInfoTable.class, cityInfoTableDao);
        registerDao(CityWeatherCurrCondTable.class, cityWeatherCurrCondTableDao);
        registerDao(WeatherIconTable.class, weatherIconTableDao);
    }
    
    public void clear() {
        cityInfoTableDaoConfig.getIdentityScope().clear();
        cityWeatherCurrCondTableDaoConfig.getIdentityScope().clear();
        weatherIconTableDaoConfig.getIdentityScope().clear();
    }

    public CityInfoTableDao getCityInfoTableDao() {
        return cityInfoTableDao;
    }

    public CityWeatherCurrCondTableDao getCityWeatherCurrCondTableDao() {
        return cityWeatherCurrCondTableDao;
    }

    public WeatherIconTableDao getWeatherIconTableDao() {
        return weatherIconTableDao;
    }

}
