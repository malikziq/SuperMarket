package com.example.supermarket.app;

import com.example.supermarket.activities.AdminActivity;
import com.example.supermarket.activities.HomeActivity;
import com.example.supermarket.activities.MainActivity;
import com.example.supermarket.fragments.AddAdminFragment;
import com.example.supermarket.fragments.CartFragment;
import com.example.supermarket.fragments.CustomerCartFragment;
import com.example.supermarket.fragments.CustomerFragment;
import com.example.supermarket.fragments.FavoritesFragment;
import com.example.supermarket.fragments.GetStartedFragment;
import com.example.supermarket.fragments.GoodsFragment;
import com.example.supermarket.fragments.LoginFragment;
import com.example.supermarket.fragments.ProfileFragment;
import com.example.supermarket.fragments.SalesFragment;
import com.example.supermarket.fragments.SignUpFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(MainActivity target);

    void inject(GetStartedFragment target);

    void inject(HomeActivity target);

    void inject(GoodsFragment target);

    void inject(SignUpFragment target);

    void inject(FavoritesFragment target);

    void inject(SalesFragment target);

    void inject(CartFragment target);

    void inject(ProfileFragment target);

    void inject(LoginFragment target);

    void inject(AdminActivity target);

    void inject(AddAdminFragment target);

    void inject(CustomerFragment target);

    void inject(CustomerCartFragment target);
}