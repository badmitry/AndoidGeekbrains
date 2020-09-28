package badmitry.hellogeekbrains.roomFavoritesCities;

import java.util.List;

public class FavoritesSource {
    private final FavoritesInterfaceDAO favoritesInterfaceDAO;
    private List<FavoriteCity> historyCities;

    public FavoritesSource(FavoritesInterfaceDAO favoritesInterfaceDAO) {
        this.favoritesInterfaceDAO = favoritesInterfaceDAO;
    }

    public List<FavoriteCity> getFavoritesCities() {
        if (historyCities == null) {
            loadCities();
        }
        return historyCities;
    }

    private void loadCities() {
        historyCities = favoritesInterfaceDAO.getAllCity();
    }

    public void addCity(FavoriteCity favoriteCity) {
        favoritesInterfaceDAO.insertCity(favoriteCity);
    }
}
