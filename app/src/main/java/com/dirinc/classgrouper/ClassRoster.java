package com.dirinc.classgrouper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionMenu;

import java.util.HashMap;

public class ClassRoster extends AppCompatActivity {
    private int classNumber;

    private View view;

    private HashMap<Integer, String> class1 = new HashMap<>();
    private HashMap<Integer, String> class2 = new HashMap<>();
    private HashMap<Integer, String> class3 = new HashMap<>();
    private HashMap<Integer, String> class4 = new HashMap<>();
    private HashMap<Integer, String> class5 = new HashMap<>();
    private HashMap<Integer, String> class6 = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_roster);

        view = findViewById(R.id.class_roster);

        Bundle bundle = getIntent().getExtras();
        classNumber = bundle.getInt("bzofghia");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionMenu fam = (FloatingActionMenu) findViewById(R.id.fab_menu);
        fam.setClosedOnTouchOutside(true);

        loadClasses();
        createRoster(1);
    }

    public void loadClasses() {
        SharedPreferences pref = null;
        switch (classNumber) {
            case 1:
                pref = getApplicationContext().getSharedPreferences("class1", Context.MODE_PRIVATE);
                break;

            case 2:
                pref = getApplicationContext().getSharedPreferences("class2", Context.MODE_PRIVATE);
                break;

            case 3:
                pref = getApplicationContext().getSharedPreferences("class3", Context.MODE_PRIVATE);
                break;

            case 4:
                pref = getApplicationContext().getSharedPreferences("class4", Context.MODE_PRIVATE);
                break;

            case 5:
                pref = getApplicationContext().getSharedPreferences("class5", Context.MODE_PRIVATE);
                break;

            case 6:
                pref = getApplicationContext().getSharedPreferences("class6", Context.MODE_PRIVATE);
                break;
        }
        if (pref != null) {
            HashMap<String, Integer> map = (HashMap<String, Integer>) pref.getAll();
            for (String s : map.keySet()) {
                String value = String.valueOf(map.get(s));
                addToMap(Integer.parseInt(s), value);
            }
        }
    }

    public void createRoster(int id) {
        RelativeLayout mainRelativeLayout = (RelativeLayout) findViewById(R.id.class_roster);

        for (int x = 0; x < getClassSize(); x++) {
            final CardView card = (CardView) findViewById(R.id.student_card);
            TextView name = (TextView) findViewById(R.id.student_name);
            ImageView leftColor = (ImageView) findViewById(R.id.student_card_color_left);
            ImageView rightColor = (ImageView) findViewById(R.id.student_card_color_right);
            name.setVisibility(View.VISIBLE);
            leftColor.setVisibility(View.VISIBLE);
            rightColor.setVisibility(View.VISIBLE);
            leftColor.setId(100 + x);
            rightColor.setId(1000 + x);
            name.setId(id + 10000);
            card.setId(id);

            leftColor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    card.setBackgroundColor(Color.parseColor("#FF999999"));
                }
            });
            name.setText(getStudentName(x));

            RelativeLayout.LayoutParams EditLayoutParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 220);

            if (id != 1) {
                EditLayoutParams.addRule(RelativeLayout.BELOW, (id - 1));
                EditLayoutParams.setMargins(0, 20, 0, 0);
            }

            card.setLayoutParams(EditLayoutParams);

            // Add it to the main RelativeLayout
            CardView cardLayout = (CardView) View.inflate(this,
                    R.layout.student, null);
            mainRelativeLayout.addView(cardLayout);
            id++;
        }
    }

    public int getClassSize() {
        switch (classNumber) {
            case 1:
                return class1.size();

            case 2:
                return class2.size();

            case 3:
                return class3.size();

            case 4:
                return class4.size();

            case 5:
                return class5.size();

            case 6:
                return class6.size();

            default:
                return 0;
        }
    }

    public String getStudentName(int key) {
        switch (classNumber) {
            case 1:
                return class1.get(key);

            case 2:
                return class2.get(key);

            case 3:
                return class3.get(key);

            case 4:
                return class4.get(key);

            case 5:
                return class5.get(key);

            case 6:
                return class6.get(key);

            default:
                return null;
        }
    }

    public void addToMap(int key, String value) {
        switch (classNumber) {
            case 1:
                class1.put(key, value);
                break;

            case 2:
                class2.put(key, value);
                break;

            case 3:
                class3.put(key, value);
                break;

            case 4:
                class4.put(key, value);
                break;

            case 5:
                class5.put(key, value);
                break;

            case 6:
                class6.put(key, value);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent changeActivities = new Intent(this, MainActivity.class);
        Log.d("ActivitySwitch", "Switching to Main Activity");
        startActivity(changeActivities);
    }
}