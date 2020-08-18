package badmitry.hellogeekbrains.adapterRLV;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import badmitry.hellogeekbrains.R;

public class AdapterForDevelopers extends RecyclerView.Adapter<AdapterForDevelopers.ViewHolder> {

    private ArrayList<String> data = new ArrayList<>();
    private int arraySize;

    public AdapterForDevelopers(ArrayList<String> data) {
        this.data.addAll(data);
        arraySize = data.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_of_developers_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String text = data.get(position);
        holder.setText(text);
    }

    @Override
    public int getItemCount() {
        return arraySize;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.list_item);
        }

        void setText(String text) {
            textView.setText(text);
        }
    }
}
