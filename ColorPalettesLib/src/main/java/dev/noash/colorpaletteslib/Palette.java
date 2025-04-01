package dev.noash.colorpaletteslib;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Palette {

    private String _id;
    private List<String> colorsList;
    @SerializedName("package_name")
    private String packageName;
    private String description;
    private String name;

    public Palette() {
    }

    public Palette(String _id, List<String> colorsList, String packageName, String description, String name) {
        this._id = _id;
        this.colorsList = colorsList;
        this.packageName = packageName;
        this.description = description;
        this.name = name;
    }

    public Palette(List<String> colorsList, String packageName, String description, String name) {
        this.colorsList = colorsList;
        this.packageName = packageName;
        this.description = description;
        this.name = name;
    }

    public List<String> getColorsList() {
        return colorsList;
    }

    public void setColorsList(List<String> colorsList) {
        this.colorsList = colorsList;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
