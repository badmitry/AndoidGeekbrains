package badmitry.hellogeekbrains.adapterRLV;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import badmitry.hellogeekbrains.R;
import badmitry.hellogeekbrains.SingletonForSaveState;

public class AdapterForWeather extends RecyclerView.Adapter<AdapterForWeather.ViewHolder> {

    private ArrayList<String[]> data;
    private int arraySize;
    Calendar calendar;
    SingletonForSaveState singletonForSaveState;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public AdapterForWeather(ArrayList<String[]> data, int arraySize) {
        this.data = data;
        this.arraySize = arraySize;
        calendar = Calendar.getInstance();
        singletonForSaveState = SingletonForSaveState.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_of_weater_list, parent, false);
        return new ViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String[] arr = data.get(position);
        holder.setTextAndPicture(arr);
    }

    @Override
    public int getItemCount() {
        return arraySize;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewWeather;
        private TextView textViewTemperature;
        private ImageView imageViewWeather;
        private TextView date;
        private TextView dayOfWeek;
        SimpleDateFormat simpleDateFormat;

        @SuppressLint("SimpleDateFormat")
        public ViewHolder(@NonNull View item) {
            super(item);
            textViewWeather = item.findViewById(R.id.textViewWeather);
            textViewTemperature = item.findViewById(R.id.textViewTemperature);
            imageViewWeather = item.findViewById(R.id.imageViewWeather);
            date = item.findViewById(R.id.date);
            dayOfWeek = item.findViewById(R.id.dayOfWeak);
            simpleDateFormat = new SimpleDateFormat("dd-MM:");
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @SuppressLint("SetTextI18n")
        void setTextAndPicture(String[] arr) {
            DayOfWeek dow = ((GregorianCalendar) calendar).toZonedDateTime().getDayOfWeek();
            String CELSIUS = "\u2103";
            date.setText(simpleDateFormat.format(calendar.getTime()));
            dayOfWeek.setText(dow.toString());
            textViewTemperature.setText(arr[0] + " " + CELSIUS);
            switch (arr[1]) {
                case "01d":
                case "01n":
                    textViewWeather.setText(R.string.sun);
                    imageViewWeather.setImageResource(R.drawable.p01d2x);
                    break;
                case "02d":
                case "02n":
                    textViewWeather.setText(R.string.cloud);
                    imageViewWeather.setImageResource(R.drawable.p02d2x);
                    break;
                case "03d":
                case "03n":
                    textViewWeather.setText(R.string.cloud);
                    imageViewWeather.setImageResource(R.drawable.p03d2x);
                    break;
                case "04d":
                case "04n":
                    textViewWeather.setText(R.string.cloud);
                    imageViewWeather.setImageResource(R.drawable.p04d2x);
                    break;
                case "09d":
                case "09n":
                    textViewWeather.setText(R.string.rainy);
                    imageViewWeather.setImageResource(R.drawable.p09d2x);
                    break;
                case "10d":
                case "10n":
                    textViewWeather.setText(R.string.rainy);
                    imageViewWeather.setImageResource(R.drawable.p10d2x);
                    break;
                case "11d":
                case "11n":
                    textViewWeather.setText(R.string.rainy);
                    imageViewWeather.setImageResource(R.drawable.p11d2x);
                    break;
                case "13d":
                case "13n":
                    textViewWeather.setText(R.string.snow);
                    imageViewWeather.setImageResource(R.drawable.p13d2x);
                    break;
                case "50d":
                case "50n":
                    textViewWeather.setText(R.string.mist);
                    imageViewWeather.setImageResource(R.drawable.p03d2x);
                    break;
            }
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
    }
}