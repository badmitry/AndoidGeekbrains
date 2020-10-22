package badmitry.hellogeekbrains;

import android.app.Application;

import androidx.room.Room;

import badmitry.hellogeekbrains.roomFavoritesCities.FavoritesCityDatabase;
import badmitry.hellogeekbrains.roomFavoritesCities.FavoritesInterfaceDAO;
import badmitry.hellogeekbrains.roomHistoryCities.CityDatabase;
import badmitry.hellogeekbrains.roomHistoryCities.InterfaceDAO;

public class App extends Application {
    private static App instance;

    private CityDatabase cityDatabase;
    private FavoritesCityDatabase favoritesCityDatabase;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        cityDatabase = Room.databaseBuilder(getApplicationContext(),
                CityDatabase.class, "city_database").allowMainThreadQueries().build();
        favoritesCityDatabase = Room.databaseBuilder(getApplicationContext(),
                FavoritesCityDatabase.class, "favorite_database").allowMainThreadQueries().build();
    }

    public InterfaceDAO getInterfaceDao() {
        return cityDatabase.getInterfaceDAO();
    }

    public FavoritesInterfaceDAO getFavoritesInterfaceDao() {
        return favoritesCityDatabase.getFavoritesInterfaceDAO();
    }
}
