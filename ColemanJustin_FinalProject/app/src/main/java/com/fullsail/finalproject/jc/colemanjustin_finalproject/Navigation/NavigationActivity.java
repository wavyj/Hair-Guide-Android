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
import com.fullsail.finalproject.jc.colemanjustin_finalproject.fragments.CreateContentFragment;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.fragments.FeedFragment;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.fragments.GuidesFragment;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.fragments.ProfileFragment;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.fragments.SearchFragment;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.util.PreferenceUtil;

public class NavigationActivity extends AppCompatActivity implements DialogInterface.OnClickListener {

    private AHBottomNavigation bottomNavigation;
    private FeedFragment mFeedFragment;
    private SearchFragment mSearchFragment;
    private CreateContentFragment mCreateContentFragment;
    private GuidesFragment mGuidesFragment;
    private ProfileFragment mProfileFragment;

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
        bottomNavigation.setBehaviorTranslationEnabled(true);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                // Toolbar changes
                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                toolbar.setTitleTextColor(ContextCompat.getColor(NavigationActivity.this, R.color.black));
                switch (position){
                    case 0:
                        toolbar.setTitle("Feed");
                        if (mFeedFragment == null){
                            mFeedFragment = FeedFragment.newInstance();
                        }
                        getFragmentManager().beginTransaction().replace(R.id.container, mFeedFragment,
                                FeedFragment.TAG).commit();
                        break;
                    case 1:
                        toolbar.setTitle("Search");
                        if (mSearchFragment == null){
                            mSearchFragment = SearchFragment.newInstance();
                        }
                        getFragmentManager().beginTransaction().replace(R.id.container, mSearchFragment,
                                SearchFragment.TAG).commit();
                        break;
                    case 2:
                        toolbar.setTitle("Create");
                        if (mCreateContentFragment == null){
                            mCreateContentFragment = CreateContentFragment.newInstance();
                        }
                        getFragmentManager().beginTransaction().replace(R.id.container, mCreateContentFragment,
                                CreateContentFragment.TAG).commit();
                        break;
                    case 3:
                        toolbar.setTitle("Guides");
                        if (mGuidesFragment == null){
                            mGuidesFragment = GuidesFragment.newInstance();
                        }
                        getFragmentManager().beginTransaction().replace(R.id.container, mGuidesFragment,
                                GuidesFragment.TAG).commit();
                        break;
                    case 4:
                        User u = PreferenceUtil.loadUserData(NavigationActivity.this);
                        toolbar.setTitle(u.getUsername().toLowerCase());
                        if (mProfileFragment == null){
                            getFragmentManager().beginTransaction().replace(R.id.container, mProfileFragment,
                                    ProfileFragment.TAG).commit();
                        }

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
