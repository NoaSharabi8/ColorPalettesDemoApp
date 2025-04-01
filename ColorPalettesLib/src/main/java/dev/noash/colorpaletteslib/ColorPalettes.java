package dev.noash.colorpaletteslib;
import android.content.Context;
import android.util.Log;

import java.util.List;
public class ColorPalettes {
    private static final PaletteController paletteController = new PaletteController();

    public interface Callback_Data<T> {
        void data(T value);
    }

    public static void getPaletteByName(Context context, String palette_name, Callback_Data<Palette> callBack) {
        if (callBack == null) {
            return;
        }

        paletteController
                .fetchPaletteByName(
                        context.getPackageName(),
                        palette_name,
                        new Callback_Palettes() {
                            @Override
                            public void ready(Palette palette) {
                                callBack.data(palette);
                            }

                            @Override
                            public void failed(String message) {
                                Log.d("Error", "failed: " + message);
                            }
                        });
    }

    public static void getPaletteNames(Context context, Callback_Data<List<PaletteInfo>> callBack) {
        if (callBack == null) {
            return;
        }
        paletteController
                .fetchPaletteNames(
                        context.getPackageName(),
                        new Callback_Names() {
                            @Override
                            public void ready(List<PaletteInfo> names) {
                                callBack.data(names);
                            }

                            @Override
                            public void failed(String message) {
                                Log.d("Error", "failed: " + message);
                            }
                        });
    }

    public static void savePalette(Context context, Palette palette, Callback_Data<String> callBack) {
        if (callBack == null || palette == null)
            return;
        palette.setPackageName(context.getPackageName());
        paletteController
                .createPalette(
                        palette,
                        new Callback_SavePalette() {

                            @Override
                            public void success(String message) {
                                callBack.data(message);
                            }

                            @Override
                            public void failed(String message) {
                                Log.d("Error", "failed: " + message);
                            }
                        });
    }

}
