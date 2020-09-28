package badmitry.hellogeekbrains.roomHistoryCities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"city", "temp"})})
public class HistoryCity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "city")
    public String city;

    @ColumnInfo(name = "temp")
    public String temp;

    @ColumnInfo(name = "data")
    public String date;
}
