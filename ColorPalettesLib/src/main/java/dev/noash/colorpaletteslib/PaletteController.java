package dev.noash.colorpaletteslib;

import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class PaletteController {
    private static final String BASE_URL = "https://api-flask-mobile-seminar.vercel.app/";
    private PaletteAPI getAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(
                        GsonConverterFactory.create(
                                new GsonBuilder()
                                        .setLenient()
                                        .create()
                        )
                )
                .build();

        return retrofit.create(PaletteAPI.class);
    }

    public void fetchPaletteByName(String package_name, String palette_name, Callback_Palettes callbackFeatures) {
        Call<Palette> call = getAPI().loadPalettesByName(package_name, palette_name);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Palette> call, Response<Palette> response) {
                callbackFeatures.ready(response.body());
            }

            @Override
            public void onFailure(Call<Palette> call, Throwable throwable) {
                callbackFeatures.failed(throwable.getMessage());
            }
        });
    }

    public void fetchPaletteNames(String package_name, Callback_Names callBackNames) {
        Call<List<PaletteInfo>> call = getAPI().loadPaletteNames(package_name);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<PaletteInfo>> call, Response<List<PaletteInfo>> response) {
                callBackNames.ready(response.body());
            }

            @Override
            public void onFailure(Call<List<PaletteInfo>> call, Throwable t) {
                callBackNames.failed(t.getMessage());
            }
        });
    }

    public void createPalette(Palette palette, Callback_SavePalette callback) {
        Call<PaletteResponse> call = getAPI().savePalette(palette);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<PaletteResponse> call, Response<PaletteResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.success("" + response.code());
                } else {
                    callback.failed("" + response.code());
                }
            }

            @Override
            public void onFailure(Call<PaletteResponse> call, Throwable t) {
                callback.failed("Error: " + t.getMessage());
            }
        });
    }

}
