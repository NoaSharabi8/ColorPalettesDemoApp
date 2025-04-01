package dev.noash.colorpaletteslib;

import java.util.List;

public interface Callback_Names {

    void ready(List<PaletteInfo> names);

    void failed(String message);

}
