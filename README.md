# 🎨 Color Palettes SDK

## 🚀 Overview
The Color Palettes SDK is a lightweight Android library that allows developers to easily create, save, and retrieve custom color palettes. Ideal for apps requiring theming, personalization, or dynamic styling, this SDK provides an effortless way to manage consistent and reusable color schemes.

<div style="display: flex; justify-content: center; align-items: center;">
  <img src="https://github.com/user-attachments/assets/cb3db27e-f6fa-45bd-8e38-207c361d8d4a" width="250" height="400">
  <img src="https://github.com/user-attachments/assets/be20af9a-a32f-4b7b-9299-5cce70d82662" width="250" height="400">
</div>

## 🛠️ Features
- *Create Palettes*: Define custom palettes with any number of colors.
- *Save Palettes*: Store palettes in the cloud using our backend API.
- *Retrieve Palettes*: Access saved palettes by name. 
- *Retrieve Palette Details*: Access concise metadata for all saved palettes (such as name and description).


## 📦 Installation
To use this SDK, add the dependency to your *Android project* using JitPack:

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.NoaSharabi8:ColorPalettesDemoApp:1.0.1'
}
```


## 🚀 Getting Started
### *1️⃣ Create a Variable for Current Theme*
```java
public class MyApplication extends Application {

    private static String selectedTheme;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static String getSelectedTheme() { return selectedTheme; }
       
    public static void setSelectedTheme(String newTheme) { selectedTheme = newTheme; }
}
```

### *2️⃣  Create and Save a Palette*
```java
List<String> colors = Arrays.asList("#FF5733", "#33FFCE", "#335BFF");
Palette newPalette = new Palette();
newPalette.setName("MyPalette");
newPalette.setDescription("A vibrant mix");
newPalette.setColorsList(colors);

ColorPalettes.createPalette(this, newPalette, new Callback_SavePalette() {
    @Override
    public void data(String value) {
        if (value.contains("201")) {
            Log.d("Palette saved successfully");
        }
        
    }
});
```

## ⭐ Get All Saved Palettes  
```java
ColorPalettes.getPaletteNames(this, new Callback_Data<List<PaletteInfo>>() {
    @Override
    public void data(List<PaletteInfo> value) {
        for (PaletteInfo info : value) {
            Log.d("Palette", info.getName() + ": " + info.getDescription());
        }
    }
});
````

## ⭐ Retrieve Palettes by Name
```java
ColorPalettes.getPaletteByName(this, "MyPalette", new Callback_Data<Palette>() {
    @Override
    public void data(Palette palette) {
        List<String> colors = palette.getColors();
        // Use colors in your app UI
    }
});
```


## 🏆 Why Use This SDK?
- 🎨 Easily manage dynamic theming and personalization.
- ☁ Connects seamlessly to backend for storage and retrieval.
- ⚡ Fast integration with minimal setup required.
- 🔐 Secure, scalable, and lightweight.
- ✅ Compatible with JitPack and modern Android libraries.

## 🤝 Contributing
Feel free to open issues or contribute via pull requests. Feedback is always welcome!

## 📜 License
This project is licensed under the *MIT License*.

---
📌 *Maintained by:*  Noa Sharabi 
