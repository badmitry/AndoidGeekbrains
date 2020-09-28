package badmitry.hellogeekbrains.roomHistoryCities;

import java.util.List;

public class CitySource {
    private final InterfaceDAO interfaceDAO;
    private List<HistoryCity> historyCities;

    public CitySource(InterfaceDAO interfaceDAO) {
        this.interfaceDAO = interfaceDAO;
    }

    public List<HistoryCity> getHistoryCities() {
        if (historyCities == null) {
            loadCities();
        }
        return historyCities;
    }

    private void loadCities() {
        historyCities = interfaceDAO.getAllCity();
    }

    public void addCity(HistoryCity historyCity) {
        interfaceDAO.insertCity(historyCity);
    }
}
