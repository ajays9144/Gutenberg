package com.igniteso.gutenberg;

import android.app.Application;

import com.igniteso.gutenberg.di.AppModule;
import com.igniteso.gutenberg.di.DaggerApiComponent;
import com.igniteso.gutenberg.di.ManagerComponent;
import com.igniteso.gutenberg.di.UtilsModule;

public class AppController extends Application {

    ManagerComponent managerComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        managerComponent = createComponent();

        managerComponent.inject(this);
    }

    public ManagerComponent getManagerComponent() {
        return managerComponent;
    }

    private ManagerComponent createComponent() {
        return DaggerApiComponent.builder().appModule(new AppModule(this)).utilsModule(new UtilsModule()).build();
    }
}
