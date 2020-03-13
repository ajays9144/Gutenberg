package com.igniteso.gutenberg.di;

import com.igniteso.gutenberg.repository.ShowBookRepository;
import com.igniteso.gutenberg.ui.feature.result.ShowBookContract;
import com.igniteso.gutenberg.ui.feature.result.ShowBookPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * The type Presenter module.
 */
@Module
public class PresenterModule {

    /**
     * Provider show book presenter show book contract . presenter.
     *
     * @param showBookRepository the show book repository
     * @return the show book contract . presenter
     */
    @Provides
    ShowBookContract.Presenter providerShowBookPresenter(ShowBookRepository showBookRepository) {
        return new ShowBookPresenter(showBookRepository);
    }
}
