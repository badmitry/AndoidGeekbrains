package badmitry.hellogeekbrains.roomHistoryCities;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface InterfaceDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCity(HistoryCity historyCity);

    @Query("SELECT * FROM HistoryCity")
    List<HistoryCity> getAllCity();
}
