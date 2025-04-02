package dev.noash.colorpalettesdemoapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;

import dev.noash.colorpaletteslib.ColorPalettes;
import dev.noash.colorpaletteslib.Palette;
import dev.noash.colorpaletteslib.PaletteInfo;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class DesignActivity extends ComponentActivity {
    private MaterialButton design_BTN_back;
    private MaterialButton BTN_theme;
    private MaterialButton BTN_new_palette;
    private int selectedOption = -1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_design);
        findViews();
        initViews();
    }
    private void initViews() {
        design_BTN_back.setOnClickListener(v -> moveToHomePage());
        BTN_new_palette.setOnClickListener(v->moveToCreatePalettePage());
        initBtnTheme();
    }
    private void initBtnTheme() {
        BTN_theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPalettes.getPaletteNames(
                        DesignActivity.this,
                        new ColorPalettes.Callback_Data<List<PaletteInfo>>() {
                            @Override
                            public void data(List<PaletteInfo> value) {
                                List<String> colorList = new ArrayList<>();
                                if(value == null) {
                                    Toast.makeText(DesignActivity.this, "There is no available palettes right now. Feel free to create one !", Toast.LENGTH_SHORT).show();
                                } else {
                                    for (PaletteInfo info : value)
                                        colorList.add(info.getName());
                                    showOptionsDialog(value);
                                }
                            }
                        }
                );
            }
        });
    }

    private void showOptionsDialog(List<PaletteInfo> paletteList) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogTheme);
        builder.setTitle("Choose Your Style");

        final int[] tempSelected = {selectedOption};
        PaletteAdapter adapter = new PaletteAdapter(this, paletteList, tempSelected[0], position -> {
            tempSelected[0] = position;
        });

        builder.setAdapter(adapter, (dialog, which) -> {
            tempSelected[0] = which;
            adapter.setSelectedPosition(which);
        });

        builder.setPositiveButton("OK", null);
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view -> {
            if (tempSelected[0] != -1) {
                PaletteInfo selectedPalette = paletteList.get(tempSelected[0]);
                MyApp.setSelectedTheme(selectedPalette.getName());
                Toast.makeText(getApplicationContext(), "Apply " + selectedPalette.getName() +" style", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            } else {
                Toast.makeText(DesignActivity.this, "Please select an option", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void moveToHomePage() {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
        finish();
    }
    private void moveToCreatePalettePage() {
        Intent i = new Intent(this, createPaletteActivity.class);
        startActivity(i);
        finish();
    }
    private void findViews() {
        design_BTN_back = findViewById(R.id.design_BTN_back);
        BTN_theme = findViewById(R.id.design_BTN_theme);
        BTN_new_palette = findViewById(R.id.design_BTN_new_palette);
    }
}
