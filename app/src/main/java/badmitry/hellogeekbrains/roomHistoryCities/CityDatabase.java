package badmitry.hellogeekbrains.roomHistoryCities;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {HistoryCity.class}, version = 1)
public abstract class CityDatabase extends RoomDatabase {
    public abstract InterfaceDAO getInterfaceDAO();
}
