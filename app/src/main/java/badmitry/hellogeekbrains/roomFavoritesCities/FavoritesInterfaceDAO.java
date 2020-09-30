package badmitry.hellogeekbrains.roomFavoritesCities;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoritesInterfaceDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCity(FavoriteCity favoriteCity);

    @Query("SELECT * FROM FavoriteCity")
    List<FavoriteCity> getAllCity();

    @Query("DELETE FROM FavoriteCity WHERE city = :text")
    void deleteCity(String text);
}
