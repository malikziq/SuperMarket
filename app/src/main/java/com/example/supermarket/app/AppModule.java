package com.example.supermarket.app;

import android.content.Context;
import android.util.Log;

import com.example.supermarket.fragments.GetStartedFragment;
import com.example.supermarket.fragments.LoginFragment;
import com.example.supermarket.fragments.SignUpFragment;
import com.example.supermarket.modelViews.CartViewModel;
import com.example.supermarket.modelViews.FavoriteViewModel;
import com.example.supermarket.modelViews.GoodsModelView;
import com.example.supermarket.modelViews.UsersModelView;
import com.example.supermarket.models.ItemsModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private static final String TAG = "AppModule";

    private App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    public GetStartedFragment provideGetStartedFragemnt() {
        Log.i(TAG, "provideGetStartedFragemnt: Getting GetStartedFragment instance");
        return new GetStartedFragment();
    }

    @Provides
    @Singleton
    public LoginFragment provideLoginFragment() {
        Log.i(TAG, "provideLoginFragment: Getting LoginFragment..");
        return new LoginFragment();
    }

    @Provides
    @Singleton
    public SignUpFragment provideSignUpFragment() {
        Log.i(TAG, "provideSignUpFragment: Getting SignUpFragment..");
        return new SignUpFragment();
    }

    @Provides
    public ItemsModel provideItemsModel() {
        return new ItemsModel();
    }

    @Provides
    public GoodsModelView provideGetStartedModelView(ItemsModel itemsModel) {
        return new GoodsModelView(itemsModel);
    }

    @Provides
    public UsersModelView providesUsersModelView() {
        return new UsersModelView();
    }

    @Provides
    public FavoriteViewModel providesFavoraiteViewModel() {
        return new FavoriteViewModel();
    }

    @Provides
    public CartViewModel provideCartViewModel(){
        return new CartViewModel();
    }

}