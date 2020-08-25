package badmitry.hellogeekbrains.adapterRLV;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import badmitry.hellogeekbrains.R;
import badmitry.hellogeekbrains.SingletonForSaveState;

public class AdapterForHistory extends RecyclerView.Adapter<AdapterForHistory.ViewHolder> {

    SingletonForSaveState singletonForSaveState;
    OnItemClicker onItemClicker;

    public AdapterForHistory(OnItemClicker onItemClicker) {
        singletonForSaveState = SingletonForSaveState.getInstance();
        this.onItemClicker = onItemClicker;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_of_history_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String[] arr = singletonForSaveState.getHistory().toArray(new String[singletonForSaveState.getHistory().size()]);
        String string = arr[position];
        holder.setText(string);
        holder.setOnClickOnItem(string);
    }

    @Override
    public int getItemCount() {
        return singletonForSaveState.getHistory().size();
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

        void setOnClickOnItem(final String text) {
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClicker != null) {
                        onItemClicker.onItemClicked(text);
                    }
                }
            });
        }
    }
}
