package badmitry.hellogeekbrains.room;

import android.app.Application;

import androidx.room.Room;

public class App extends Application {
    private static App instance;

    private CityDatabase db;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        db = Room.databaseBuilder(getApplicationContext(),
                CityDatabase.class, "city_database").allowMainThreadQueries().build();
    }

    public InterfaceDAO getInterfaceDao() {
        return db.getInterfaceDAO();
    }
}
