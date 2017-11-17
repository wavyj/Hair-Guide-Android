package com.fullsail.finalproject.jc.colemanjustin_finalproject.Activities;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.R;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.auth.AuthenticationActivity;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.data.User;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.fragments.CreateContentFragment;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.fragments.FeedFragment;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.fragments.GuidesFragment;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.fragments.ProfileFragment;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.fragments.SearchFragment;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.util.PreferenceUtil;

public class NavigationActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener{

    private AHBottomNavigation bottomNavigation;
    private FeedFragment mFeedFragment;
    private SearchFragment mSearchFragment;
    private CreateContentFragment mCreateContentFragment;
    private GuidesFragment mGuidesFragment;
    private ProfileFragment mProfileFragment;

    private int currentItem = 0;

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
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.guides, R.drawable.icon_guides,
                R.color.colorAccent);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.profile, R.drawable.icon_profile,
                R.color.colorAccent);
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);

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
                toolbar.setOnMenuItemClickListener(NavigationActivity.this);
                toolbar.setTitleTextColor(ContextCompat.getColor(NavigationActivity.this, R.color.black));
                toolbar.getMenu().clear();
                switch (position){
                    case 0:
                        toolbar.setTitle("Feed");
                        if (mFeedFragment == null){
                            mFeedFragment = FeedFragment.newInstance();
                        }
                        invalidateOptionsMenu();
                        getFragmentManager().beginTransaction().replace(R.id.container, mFeedFragment,
                                FeedFragment.TAG).commit();
                        toolbar.getMenu().clear();
                        toolbar.inflateMenu(R.menu.posts_menu);
                        break;
                    case 1:
                        toolbar.setTitle("Search");
                        if (mSearchFragment == null){
                            mSearchFragment = SearchFragment.newInstance();
                        }
                        invalidateOptionsMenu();
                        getFragmentManager().beginTransaction().replace(R.id.container, mSearchFragment,
                                SearchFragment.TAG).commit();
                        break;
                    case 2:
                        toolbar.setTitle("Guides");
                        invalidateOptionsMenu();
                        if (mGuidesFragment == null){
                            mGuidesFragment = GuidesFragment.newInstance();
                        }
                        getFragmentManager().beginTransaction().replace(R.id.container, mGuidesFragment,
                                GuidesFragment.TAG).commit();
                        toolbar.inflateMenu(R.menu.guide_menu);
                        break;
                    case 3:
                        invalidateOptionsMenu();
                        User u = PreferenceUtil.loadUserData(NavigationActivity.this);
                        toolbar.setTitle(u.getUsername().toLowerCase());
                        if (mProfileFragment == null){
                            mProfileFragment = ProfileFragment.newInstance(PreferenceUtil.loadUserData
                                    (NavigationActivity.this));
                        }
                        getFragmentManager().beginTransaction().replace(R.id.container, mProfileFragment,
                                ProfileFragment.TAG).commit();
                        toolbar.inflateMenu(R.menu.settings_menu);
                }
                return true;
            }
        });

        bottomNavigation.setCurrentItem(0);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.addPost:
                Intent createPostIntent = new Intent(NavigationActivity.this, CreatePostActivity.class);
                startActivity(createPostIntent);
                break;
            case R.id.addGuide:
                break;
            case R.id.signout:
                PreferenceUtil.deleteUserData(NavigationActivity.this);
                Intent authIntent = new Intent(NavigationActivity.this, AuthenticationActivity.class);
                startActivity(authIntent);
                finish();
        }
        return true;
    }
}
