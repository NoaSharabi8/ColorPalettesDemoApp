package dev.noash.colorpalettesdemoapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import dev.noash.colorpaletteslib.ColorPalettes;
import dev.noash.colorpaletteslib.Palette;

public class HomeActivity extends AppCompatActivity {
    private LinearLayout mainLayout;
    private DrawerLayout home_drawerLayout;
    private NavigationView home_navigationView;
    private Toolbar home_toolbar;
    private  TextView home_TV_hello_message;
    private  TextView home_TV_app_name;
    private MaterialButton home_BTN_new_appointment;
    private MaterialButton home_BTN_my_appointment;
    private  TextView home_TV_info;
    private  TextView home_TV_info_box;
    private final int BLACK = Color.parseColor("#000000");
    private final int WHITE = Color.parseColor("#FFFFFF");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        findViews();
        initViews();
        applyDesign();
    }

    private void applyDesign() {
        if(MyApp.getSelectedTheme() != null) {
        String theme=MyApp.getSelectedTheme();
        ColorPalettes.getPaletteByName(
                HomeActivity.this,
                theme,
                new ColorPalettes.Callback_Data<Palette>() {
                    @Override
                    public void data(Palette value) {
                        List<String> colorList = value.getColorsList();
                        setColorsByList(colorList);
                    }
                }
        );
      }
    }

    private void setColorsByList(List<String> colorList) {
        if(colorList.get(0).compareTo("#7FFFFF") > 0)
        {
            mainLayout.setBackgroundColor(Color.parseColor("#D8B4C8"));
        }

        if(colorList.size()==2) {
            setTextsColor(Color.parseColor(colorList.get(0)));
            setButtonsColor(Color.parseColor(colorList.get(1)));
        }
        if(colorList.size()==3) {
            setTextsColor(Color.parseColor(colorList.get(0)));
            setButtonsColor(Color.parseColor(colorList.get(1)));
            setButtonsColor(Color.parseColor(colorList.get(2)));
        }
    }
    private void setLogoColor(int color) {
        home_TV_app_name.setTextColor(color);
    }
    private void setTextsColor(int color) {
        home_TV_hello_message.setTextColor(color);
        home_TV_info.setTextColor(color);
    }

    private void setButtonsColor(int color) {
        int textColor = WHITE;
        home_BTN_my_appointment.setBackgroundColor(color);
        home_BTN_new_appointment.setBackgroundColor(color);
        if(isColorBright(color))
            textColor = BLACK;
        home_BTN_my_appointment.setTextColor(textColor);
        home_BTN_new_appointment.setTextColor(textColor);

    }

    public static boolean isColorBright(int color) {
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);

        // (Perceived Brightness)
        double brightness = (0.299 * r + 0.587 * g + 0.114 * b);
        return brightness > 186; // 1- color is bright , 0- color is dark
    }

    private void initViews() {
        setSupportActionBar(home_toolbar);
        home_TV_info_box.setText(Html.fromHtml(getString(R.string.home_TV_info), Html.FROM_HTML_MODE_LEGACY));
        menuManagement();
    }
    private void menuManagement() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, home_drawerLayout, home_toolbar, R.string.open, R.string.close);
        home_drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        home_navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_design) {
                    moveToDesignPage();
                }
                home_drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
    private void findViews() {
        home_drawerLayout = findViewById(R.id.home_drawer_layout);
        home_navigationView = findViewById(R.id.home_navigation_view);
        home_toolbar = findViewById(R.id.home_toolbar);
        home_TV_info_box = findViewById(R.id.home_TV_info_box);
        home_TV_info = findViewById(R.id.home_TV_info);
        home_TV_app_name= findViewById(R.id.home_TV_app_name);
        home_BTN_new_appointment= findViewById(R.id.home_BTN_new_appointment);
        home_BTN_my_appointment = findViewById(R.id.home_BTN_my_appointments);
        home_TV_hello_message = findViewById(R.id.home_TV_hello_message);
        mainLayout = findViewById(R.id.home_main_layout);
    }
    private void moveToDesignPage() {
        Intent i = new Intent(this, DesignActivity.class);
        Bundle bundle = new Bundle();
        i.putExtras(bundle);
        startActivity(i);
        finish();
    }
}
