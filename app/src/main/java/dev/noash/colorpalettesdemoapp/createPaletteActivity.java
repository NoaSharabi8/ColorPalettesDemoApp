package dev.noash.colorpalettesdemoapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;

import com.google.android.material.button.MaterialButton;
import com.skydoves.colorpickerview.AlphaTileView;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;
import com.skydoves.colorpickerview.sliders.BrightnessSlideBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dev.noash.colorpaletteslib.ColorPalettes;
import dev.noash.colorpaletteslib.Palette;


public class createPaletteActivity extends ComponentActivity {
    private ColorPickerView colorPickerView;
    private BrightnessSlideBar brightnessSlideBar;
    private AlphaTileView flagColorView;
    private TextView flagColorCode;
    private MaterialButton BTN_add_color;
    private MaterialButton BTN_save_palette;
    private MaterialButton create_palette_BTN_back;
    private int selectedColor = Color.WHITE; // ברירת מחדל
    private static List<String> colorList = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_palette);
        findViews();
        initViews();
    }
    private void initViews() {
        colorPickerView.attachBrightnessSlider(brightnessSlideBar);
        BTN_add_color.setOnClickListener(v -> addColorToList());
        create_palette_BTN_back.setOnClickListener(v -> moveToDesignPage());
        BTN_save_palette.setOnClickListener(v -> savePalette());
        initColorPickerView();
    }
    private void initColorPickerView() {
        colorPickerView.setColorListener(new ColorEnvelopeListener() {
            @Override
            public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                selectedColor = envelope.getColor(); //save color
                flagColorView.setPaintColor(selectedColor);
                String hex = String.format("#%06X", (0xFFFFFF & selectedColor));
                flagColorCode.setText(hex);
            }
        });
    }
    private void addColorToList() {
        if(colorList.size() < 3 ) {
            String hex = String.format("#%06X", (0xFFFFFF & selectedColor));
            colorList.add(hex);
            String s = "Color "+hex+" added to list";
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Can't add more than 3 colors in palette", Toast.LENGTH_LONG).show();
        }

    }
    private void moveToDesignPage() {
        Intent i = new Intent(this, DesignActivity.class);
        startActivity(i);
        finish();
    }
    private void findViews() {
        colorPickerView = findViewById(R.id.create_palette_colorPickerView);
        brightnessSlideBar = findViewById(R.id.create_palette_brightnessSlideBar);
        flagColorView = findViewById(R.id.create_palette_flag_color);
        flagColorCode = findViewById(R.id.create_palette_flag_color_code);
        BTN_add_color = findViewById(R.id.create_palette_btn_add_color);
        BTN_save_palette = findViewById(R.id.create_palette_btn_save_palette);
        create_palette_BTN_back = findViewById(R.id.create_palette_BTN_back);
    }
    private void savePalette() {
        if (colorList.isEmpty()) {
            Toast.makeText(this, "No colors saved yet", Toast.LENGTH_SHORT).show();
            return;
        }
        if (colorList.size() < 2) {
            Toast.makeText(this, "Minimum 2 Colors in palette", Toast.LENGTH_SHORT).show();
            return;
        }
        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_palette_name, null);
        final TextView colorsPreview = dialogView.findViewById(R.id.dialog_colors_preview);
        final android.widget.EditText editTextName = dialogView.findViewById(R.id.dialog_palette_name_input);
        final android.widget.EditText editTextDescription = dialogView.findViewById(R.id.dialog_palette_description_input);
        StringBuilder message = new StringBuilder();
        for (int i = 0; i < colorList.size(); i++) {
            message.append(i + 1).append(". ").append(colorList.get(i)).append("\n");
        }
        colorsPreview.setText(message.toString());

        TextView popup_title = new TextView(this);
        popup_title.setText("Saved Colors :");
        popup_title.setTextDirection(View.TEXT_DIRECTION_LTR);
        popup_title.setTextSize(20);
        popup_title.setPadding(32, 32, 32, 16);

        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setCustomTitle(popup_title)
                .setView(dialogView)
                .setPositiveButton("Save", (dialog, which) -> {
                    String paletteName = editTextName.getText().toString();
                    String description = editTextDescription.getText().toString();
                    if (paletteName.isEmpty()) {
                        Toast.makeText(this, "Please enter a name for the palette", Toast.LENGTH_SHORT).show();
                    } else {
                        addPaletteToDB(paletteName, description, colorList);
                    }
                })
                .setNegativeButton("Return", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void addPaletteToDB(String paletteName, String description, List<String> colorList) {
        Palette newPalette = new Palette();
        newPalette.setName(paletteName);
        newPalette.setDescription(description);
        Collections.sort(colorList);
        newPalette.setColorsList(colorList);

        ColorPalettes.savePalette(
                createPaletteActivity.this,
                newPalette,
                new ColorPalettes.Callback_Data<String>() {
                    @Override
                    public void data(String value) {
                        if(value.contains("201")) {
                            Toast.makeText(createPaletteActivity.this, "Palette saved successfully", Toast.LENGTH_SHORT).show();
                            initColorList();
                        }
                    }
                }
        );
    }
    private static void initColorList() {
        colorList = new ArrayList<>();
    }
}