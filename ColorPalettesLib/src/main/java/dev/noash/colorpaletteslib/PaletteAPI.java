package dev.noash.colorpaletteslib;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
public interface PaletteAPI {

    @GET("/color-palette/{package_name}/{palette_name}")
    Call<Palette> loadPalettesByName(
            @Path(value= "package_name", encoded = true) String _package_name,
            @Path(value = "palette_name", encoded = true) String _palette_name
    );

    @GET("/color-palette/{package_name}")
    Call<List<PaletteInfo>> loadPaletteNames(
            @Path(value= "package_name", encoded = true) String _package_name
    );

    @POST("/color-palette")
    Call<PaletteResponse> savePalette(@Body Palette paletteRequest);

}
