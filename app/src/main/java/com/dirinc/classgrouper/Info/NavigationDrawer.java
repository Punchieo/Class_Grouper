package com.dirinc.classgrouper.Info;

import android.app.Activity;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dirinc.classgrouper.Activity.Settings;
import com.dirinc.classgrouper.R;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ContainerDrawerItem;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.HashMap;
import java.util.Set;

public class NavigationDrawer {
    private AppCompatActivity activity;
    private ActionBar actionBar;
    private Toolbar toolbar;

    private Drawer drawer;
    private DrawerLayout drawerLayout;
    private AccountHeader accountHeader;

    public HashMap<String, Integer> drawerKey = new HashMap<>(); //Starts at 1

    public NavigationDrawer(AppCompatActivity activity, Toolbar toolbar) {
        this.activity = activity;
        this.toolbar = toolbar;
        this.actionBar = activity.getSupportActionBar();
    }

    public NavigationDrawer initialize() {
        accountHeader = new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.drawable.classroom)
                .build();

        drawer = new DrawerBuilder()
                .withActivity(activity)
                .withToolbar(toolbar)
                .withAccountHeader(accountHeader)
                .withActionBarDrawerToggle(false)
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withName("Home")
                                .withIcon(GoogleMaterial.Icon.gmd_home),
                        new DividerDrawerItem()
                )
                .build();
        drawerKey.put("Home", 1);
        drawerKey.put("Divider1", 2);
        drawerLayout = drawer.getDrawerLayout();
        drawerLayout.setScrimColor(Color.parseColor("#80FFFFFF"));

        addHamburgerAnimation();

        return this;
    }

    public NavigationDrawer addItem(String type, String name, IIcon drawable, int position,
                                    Drawer.OnDrawerItemClickListener onClick) {
        switch (type) {
            case "PrimaryDrawerItem":
                drawer.addItemAtPosition(new PrimaryDrawerItem()
                        .withName(name)
                        .withIcon(drawable)
                        .withOnDrawerItemClickListener(onClick),
                        position);
                break;

            case "SecondaryDrawerItem":
                drawer.addItemAtPosition(new SecondaryDrawerItem()
                                .withName(name)
                                .withIcon(drawable)
                                .withOnDrawerItemClickListener(onClick),
                        position);
                break;

            case "Divider":
                drawer.addItemAtPosition(new DividerDrawerItem(), position);
                break;
        }

        drawerKey.put(name, position);

        return this;
    }

    public NavigationDrawer addItem(String type, String name, Drawable drawable, int position,
                                    Drawer.OnDrawerItemClickListener onClick) {
        switch (type) {
            case "PrimaryDrawerItem":
                drawer.addItemAtPosition(new PrimaryDrawerItem()
                                .withName(name)
                                .withIcon(drawable)
                                .withOnDrawerItemClickListener(onClick),
                        position);
                break;

            case "SecondaryDrawerItem":
                drawer.addItemAtPosition(new SecondaryDrawerItem()
                                .withName(name)
                                .withIcon(drawable)
                                .withOnDrawerItemClickListener(onClick),
                        position);
                break;

            case "Divider":
                drawer.addItemAtPosition(new DividerDrawerItem(), position);
                break;
        }

        drawerKey.put(name, position);

        return this;
    }

    public void removeItem(int position) {
        drawer.removeItemByPosition(position);

        HashMap<Integer, String> drawerKeyInv = new HashMap<>();
        for(HashMap.Entry<String, Integer> entry : drawerKey.entrySet()){
            drawerKeyInv.put(entry.getValue(), entry.getKey());
        }
        drawerKey.remove(drawerKeyInv.get(position));
    }

    public void removeItem(String name) {
        drawer.removeItemByPosition(drawerKey.get(name));
        drawerKey.remove(name);
    }

    public NavigationDrawer addFooter(String text, IIcon drawable, View.OnClickListener onClick, int position) {
        drawer.addStickyFooterItem(new PrimaryDrawerItem()
                .withName(text)
                .withSelectable(false)
                .withIcon(drawable));

        LinearLayout footer = (LinearLayout) drawer.getStickyFooter();
        LinearLayout settings = (LinearLayout) footer.getChildAt(0);
        LinearLayout feedback = (LinearLayout) footer.getChildAt(1);
        settings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity.getApplicationContext(), Settings.class));
                }
            });
        if (feedback != null) {
            feedback.setOnClickListener(onClick);
        }

        return this;
    }

    public void removeHamburgerAnimation() {
        activity.getSupportActionBar().setHomeAsUpIndicator(new IconicsDrawable(activity.getApplicationContext())
                .icon(GoogleMaterial.Icon.gmd_arrow_back).color(Color.WHITE).sizeDp(16));
    }

    private void addHamburgerAnimation() {
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                activity, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close
        );
        drawerLayout.addDrawerListener(drawerToggle);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        drawerToggle.syncState();
    }

    public Drawer getDrawer() {
        return drawer;
    }
}