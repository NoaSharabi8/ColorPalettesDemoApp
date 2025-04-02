package dev.noash.colorpalettesdemoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import dev.noash.colorpaletteslib.PaletteInfo;

public class PaletteAdapter extends ArrayAdapter<PaletteInfo> {
    private OnItemSelectedListener itemSelectedListener;
    private int selectedPosition;
    public interface OnItemSelectedListener {
        void onItemSelected(int position);
    }

    public PaletteAdapter(Context context, List<PaletteInfo> palettes, int selectedPosition, OnItemSelectedListener listener) {
        super(context, 0, palettes);
        this.selectedPosition = selectedPosition;
        this.itemSelectedListener = listener;
    }

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        PaletteInfo palette = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.dialog_item_with_description, parent, false);
        }

        TextView name = convertView.findViewById(R.id.item_name);
        TextView description = convertView.findViewById(R.id.item_description);
        RadioButton radioButton = convertView.findViewById(R.id.radio_button);
        name.setText(palette.getName());
        description.setText(palette.getDescription());
        radioButton.setChecked(position == selectedPosition);

        radioButton.setOnClickListener(v -> {
            selectedPosition = position;
            if (itemSelectedListener != null) {
                itemSelectedListener.onItemSelected(position);
            }
            notifyDataSetChanged();
        });

        convertView.setOnClickListener(v -> {
            selectedPosition = position;
            if (itemSelectedListener != null) {
                itemSelectedListener.onItemSelected(position);
            }
            notifyDataSetChanged();
        });
        return convertView;
    }
}


