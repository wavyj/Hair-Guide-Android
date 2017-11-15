package com.fullsail.finalproject.jc.colemanjustin_finalproject.Navigation;

import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.R;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.data.User;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.util.PreferenceUtil;

public class NavigationActivity extends AppCompatActivity implements DialogInterface.OnClickListener {

    private AHBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        // Bottom Navigation Bar Setup
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottomNav);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.home, R.drawable.icon_home,
                R.color.colorAccent);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.search, R.drawable.icon_search,
                R.color.colorAccent);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.add, R.drawable.icon_add,
                R.color.colorAccent);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.guides, R.drawable.icon_guides,
                R.color.colorAccent);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem(R.string.profile, R.drawable.icon_profile,
                R.color.colorAccent);
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);
        bottomNavigation.addItem(item5);

        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);
        bottomNavigation.setDefaultBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        bottomNavigation.setInactiveColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        bottomNavigation.setAccentColor(ContextCompat.getColor(this, R.color.colorAccent));

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                // Toolbar changes
                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                toolbar.setTitleTextColor(ContextCompat.getColor(NavigationActivity.this, R.color.black));
                switch (position){
                    case 0:
                        toolbar.setTitle("Feed");
                        break;
                    case 1:
                        toolbar.setTitle("Search");
                        break;
                    case 2:
                        new AlertDialog.Builder(NavigationActivity.this).setItems(R.array.createOptions,
                                NavigationActivity.this).show();
                        break;
                    case 3:
                        toolbar.setTitle("Guides");
                        break;
                    case 4:
                        User u = PreferenceUtil.loadUserData(NavigationActivity.this);
                        toolbar.setTitle(u.getUsername().toLowerCase());
                }
                return true;
            }
        });

        bottomNavigation.setCurrentItem(0);
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        switch (i){
            case 0:
                // To Create Post
                bottomNavigation.setCurrentItem(0);
                break;
            case 1:
                // To Create Guide
                bottomNavigation.setCurrentItem(3);
                break;
        }

    }
}
