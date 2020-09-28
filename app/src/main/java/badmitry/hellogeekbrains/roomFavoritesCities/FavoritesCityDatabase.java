package badmitry.hellogeekbrains.roomFavoritesCities;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {FavoriteCity.class}, version = 1)
public abstract class FavoritesCityDatabase extends RoomDatabase {
    public abstract FavoritesInterfaceDAO getFavoritesInterfaceDAO();
}
