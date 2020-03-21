package com.igniteso.gutenberg.di;

import com.igniteso.gutenberg.AppController;
import com.igniteso.gutenberg.repository.ShowBookRepository;
import com.igniteso.gutenberg.ui.feature.result.ShowBookResultActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * The interface Manager component.
 */
@Singleton
@Component(modules = {AppModule.class, UtilsModule.class, PresenterModule.class})
public interface ManagerComponent {
    /**
     * Inject.
     *
     * @param appController the app controller
     */
    void inject(AppController appController);

    /**
     * Inject.
     *
     * @param showBookResultActivity the show book result activity
     */
    void inject(ShowBookResultActivity showBookResultActivity);
}
