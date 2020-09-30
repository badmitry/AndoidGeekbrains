package badmitry.hellogeekbrains.adapterRLV;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import badmitry.hellogeekbrains.R;
import badmitry.hellogeekbrains.roomFavoritesCities.FavoriteCity;

public class AdapterChooseCity extends RecyclerView.Adapter<AdapterChooseCity.ViewHolder> {

    private ArrayList<String> data = new ArrayList<>();
    OnItemClicker onItemClicker;
    OnButtonDeleteClicker onButtonDeleteClicker;

    public AdapterChooseCity(List<FavoriteCity> listData, OnItemClicker onItemClicker, OnButtonDeleteClicker onButtonDeleteClicker) {
        for (FavoriteCity favoriteCity : listData) {
            data.add(favoriteCity.city);
        }
        this.onItemClicker = onItemClicker;
        this.onButtonDeleteClicker = onButtonDeleteClicker;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_of_cities_list, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String text = data.get(position);
        holder.setTextToTextView(text);
        holder.setOnClickOnItem(text);
        holder.setOnClickOnButtonDelete(text);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private Button button;

        public ViewHolder(@NonNull View item) {
            super(item);
            textView = item.findViewById(R.id.list_item);
            button = item.findViewById(R.id.button_delete);
        }

        void setTextToTextView(String text) {
            textView.setText(text);
        }

        void setOnClickOnItem(final String text) {
            textView.setOnClickListener(view -> {
                if (onItemClicker != null) {
                    onItemClicker.onItemClicked(text);
                }
            });
        }

        void setOnClickOnButtonDelete(final String text) {
            button.setOnClickListener(view -> {
                if (onButtonDeleteClicker != null) {
                    onButtonDeleteClicker.onButtonDeleteClick(text);
                }
            });
        }
    }
}
