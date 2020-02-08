package com.example.supermarket.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.supermarket.R;
import com.example.supermarket.fragments.CartFragment;
import com.example.supermarket.fragments.ContactsFragment;
import com.example.supermarket.fragments.FavoritesFragment;
import com.example.supermarket.fragments.GoodsFragment;
import com.example.supermarket.fragments.ProfileFragment;
import com.example.supermarket.fragments.SalesFragment;
import com.example.supermarket.fragments.UserHomeFragment;
import com.shrikanthravi.customnavigationdrawer2.data.MenuItem;
import com.shrikanthravi.customnavigationdrawer2.widget.SNavigationDrawer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";

    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.navigationDrawer)
    SNavigationDrawer navigationDrawer;

    private List<MenuItem> menuItemList;

    private Class fragmentClass;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        Log.i(TAG, "onCreate: HomeActivity...");

        // Bind ButterKnife
        ButterKnife.bind(this);

        // Set Menu Items for Navigation Drawer
        setMenuItemList();
        navigationDrawer.setMenuItemList(menuItemList);

        // Navigation Drawer Fragment swirching Listener
        navigationDrawerListener();


        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.frameLayout, new UserHomeFragment()).commit();
    }

    private void setMenuItemList() {
        menuItemList = new ArrayList<>();
        menuItemList.add(new MenuItem("Home", R.drawable.menu_item_bg));
        menuItemList.add(new MenuItem("Goods", R.drawable.menu_item_bg));
        menuItemList.add(new MenuItem("Cart", R.drawable.menu_item_bg));
        menuItemList.add(new MenuItem("Sales", R.drawable.menu_item_bg));

        menuItemList.add(new MenuItem("Favorites", R.drawable.menu_item_bg));
        menuItemList.add(new MenuItem("Profile", R.drawable.menu_item_bg));
        menuItemList.add(new MenuItem("Contact Us", R.drawable.menu_item_bg));
        menuItemList.add(new MenuItem("Logout", R.drawable.menu_item_bg));
    }

    private void navigationDrawerListener() {
        navigationDrawer.setOnMenuItemClickListener(new SNavigationDrawer.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClicked(int position) {
                Log.i(TAG, "onMenuItemClicked: Ment New Fragment postion: " + position);

                switch (position) {

                    case 0:
                        fragmentClass = UserHomeFragment.class;
                        break;

                    case 1:
                        fragmentClass = GoodsFragment.class;
                        break;

                    case 3:
                        fragmentClass = SalesFragment.class;
                        break;

                    case 2:
                        fragmentClass = CartFragment.class;
                        break;

                    case 4:
                        fragmentClass = FavoritesFragment.class;
                        break;

                    case 5:
                        fragmentClass = ProfileFragment.class;
                        break;

                    case 6:
                        fragmentClass = ContactsFragment.class;
                        break;

                    case 7:
                        goToMainActivity();
                        break;
                }
            }
        });

        navigationDrawer.setDrawerListener(new SNavigationDrawer.DrawerListener() {
            @Override
            public void onDrawerOpening() {

            }

            @Override
            public void onDrawerClosing() {
                System.out.println("Drawer closed");

                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                            .replace(R.id.frameLayout, fragment).commit();

                }
            }

            @Override
            public void onDrawerOpened() {

            }

            @Override
            public void onDrawerClosed() {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.EXTRA_MESSAGE, "yes");
        startActivity(intent);

        finish();
    }


}
