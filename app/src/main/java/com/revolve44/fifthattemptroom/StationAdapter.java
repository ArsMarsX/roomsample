package com.revolve44.fifthattemptroom;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class  StationAdapter extends RecyclerView.Adapter<StationAdapter.StationHolder> {
    private List<Station> stations = new ArrayList<>();
    @NonNull
    @Override
    public StationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);

        Log.d(TAG, "onBindViewHolder: TTT"+ stations.get(1).toString());
        System.out.println(stations + " 777");

        return new StationHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull StationHolder holder, int position) {

        Station currentStation = stations.get(position);
        holder.textViewTitle.setText(currentStation.getTitle());
        holder.textViewDescription.setText(currentStation.getDescription());
        holder.textViewPriority.setText(String.valueOf(currentStation.getPriority()));
    }
    @Override
    public int getItemCount() {
        return stations.size();
    }
    public void setStations(List<Station> stations) {
        this.stations = stations;
        notifyDataSetChanged(); // recyclerview refresh
    }
    class StationHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;
        public StationHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);
        }
    }
}
