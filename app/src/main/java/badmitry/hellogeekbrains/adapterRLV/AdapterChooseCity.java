package badmitry.hellogeekbrains.adapterRLV;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import badmitry.hellogeekbrains.R;

public class AdapterChooseCity extends RecyclerView.Adapter<AdapterChooseCity.ViewHolder> {

    private ArrayList<String> data;
    OnItemClicker onItemClicker;

    public AdapterChooseCity(ArrayList<String> data, OnItemClicker onItemClicker) {
        this.data = data;
        this.onItemClicker = onItemClicker;
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
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public ViewHolder(@NonNull View item) {
            super(item);
            textView = item.findViewById(R.id.list_item);
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
    }
}
