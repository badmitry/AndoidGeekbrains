package badmitry.hellogeekbrains;

import android.util.Log;
import java.io.Serializable;

public class SingletonForSaveState implements Serializable {

    private String city;
    private static SingletonForSaveState instance;
    MainActivity mainActivity;

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void setCity(String city) {
        if (city != null && !city.equals("")) {
            this.city = city;
            Log.d("!!!", this.city);
            mainActivity.startCreateMainScreen();
        }
    }

    public String getCity() {
        return city;
    }

    private SingletonForSaveState() {
    }

    public static SingletonForSaveState getInstance() {
        if (instance == null) {
            instance = new SingletonForSaveState();
        }
        return instance;
    }

    public boolean isCity() {
        return city != null;
    }
}
