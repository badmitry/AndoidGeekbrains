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
import java.util.ArrayList;
import java.util.Calendar;

import badmitry.hellogeekbrains.R;
import badmitry.hellogeekbrains.SingletonForSaveState;

public class AdapterForWeather extends RecyclerView.Adapter<AdapterForWeather.ViewHolder> {

    private ArrayList<int[]> data;
    private int arraySize;
    Calendar calendar;
    SingletonForSaveState singletonForSaveState;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public AdapterForWeather(ArrayList<int[]> data, int arraySize) {
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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int[] arr = data.get(position);
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
        SimpleDateFormat simpleDateFormat;

        @SuppressLint("SimpleDateFormat")
        public ViewHolder(@NonNull View item) {
            super(item);
            textViewWeather = item.findViewById(R.id.textViewWeather);
            textViewTemperature = item.findViewById(R.id.textViewTemperature);
            imageViewWeather = item.findViewById(R.id.imageViewWeather);
            date = item.findViewById(R.id.date);
            simpleDateFormat = new SimpleDateFormat("dd-MM:");
        }

        @SuppressLint("SetTextI18n")
        void setTextAndPicture(int[] arr) {
            String CELSIUS = "\u2103";
            date.setText(simpleDateFormat.format(calendar.getTime()));
            textViewTemperature.setText(arr[0] + " " + CELSIUS);
            if (arr[1] == 1) {
                textViewWeather.setText(R.string.sun);
                if (singletonForSaveState.isDarkTheme()) {
                    imageViewWeather.setImageResource(R.drawable.sun_dark);
                } else {
                    imageViewWeather.setImageResource(R.drawable.sun);
                }
            } else if (arr[1] == 2) {
                textViewWeather.setText(R.string.rainy);
                if (singletonForSaveState.isDarkTheme()) {
                    imageViewWeather.setImageResource(R.drawable.rain_dark);
                } else {
                    imageViewWeather.setImageResource(R.drawable.rain);
                }
            }
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
    }
}