package com.igniteso.gutenberg.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * The type App module.
 */
@Module
public class AppModule {

    private Application application;

    /**
     * Instantiates a new App module.
     */
    public AppModule() {

    }

    /**
     * Instantiates a new App module.
     *
     * @param application the application
     */
    public AppModule(Application application) {
        this.application = application;
    }

    /**
     * Application application.
     *
     * @return the application
     */
    @Singleton
    @Provides
    public Application application() {
        return this.application;
    }
}
