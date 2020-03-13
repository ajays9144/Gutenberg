package com.igniteso.gutenberg.di;

import android.app.Application;

import com.google.gson.Gson;
import com.igniteso.gutenberg.AppController;
import com.igniteso.gutenberg.network.ApiCallInterface;
import com.igniteso.gutenberg.repository.ShowBookRepository;
import com.igniteso.gutenberg.ui.feature.result.ShowBookContract;
import com.igniteso.gutenberg.ui.feature.result.ShowBookResultActivity;
import com.igniteso.gutenberg.ui.feature.result.ShowBookResultActivity_MembersInjector;

import javax.inject.Provider;

import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * The type Dagger api component.
 */
public class DaggerApiComponent implements ManagerComponent {

    private PresenterModule presenterModule;
    private Provider<Gson> providerGsonProvider;
    private Provider<OkHttpClient> getRequestHeaderProvider;
    private Provider<Retrofit> provideRetrofitProvider;
    private Provider<Application> providesApplicationProvider;
    private Provider<ApiCallInterface> getApiCallInterfaceProvider;
    private Provider<ShowBookRepository> showBookRepositoryProvider;

    private DaggerApiComponent(Builder builder) {
        initialize(builder);
    }

    /**
     * Builder builder.
     *
     * @return the builder
     */
    public static Builder builder() {
        return new Builder();
    }

    private void initialize(final Builder builder) {
        this.providesApplicationProvider = DoubleCheck.provider(AppModule_ApplicationFactory.create(builder.appModule));
        this.providerGsonProvider = DoubleCheck.provider(UtilsModule_ProvideGsonFactory.create(builder.utilsModule));
        this.getRequestHeaderProvider = DoubleCheck.provider(UtilsModule_GetRequestHeaderFactory.create(builder.utilsModule));
        this.presenterModule = builder.presenterModule;
        this.provideRetrofitProvider = DoubleCheck.provider(UtilsModule_ProvideRetrofitFactory.create(builder.utilsModule, providerGsonProvider, getRequestHeaderProvider));
        this.getApiCallInterfaceProvider = DoubleCheck.provider(UtilsModule_GetApiCallInterfaceFactory.create(builder.utilsModule, provideRetrofitProvider));
        this.showBookRepositoryProvider = DoubleCheck.provider(UtilsModule_ProviderShowBookRepositoryFactory.create(builder.utilsModule, providesApplicationProvider, getApiCallInterfaceProvider));
    }

    private ShowBookContract.Presenter getShowBookActivityPresenter() {
        return PresenterModule_ProviderShowBookPresenterFactory.proxyProviderShowBookPresenter(this.presenterModule, showBookRepositoryProvider.get());
    }

    @Override
    public void inject(AppController appController) {
        injectAppController(appController);
    }

    @Override
    public void inject(ShowBookResultActivity showBookResultActivity) {
        injectShowBookResultActivity(showBookResultActivity);
    }

    private AppController injectAppController(AppController instance) {
        return instance;
    }

    private ShowBookResultActivity injectShowBookResultActivity(ShowBookResultActivity instance) {
        ShowBookResultActivity_MembersInjector.injectPresenter(instance, getShowBookActivityPresenter());
        return instance;
    }

    /**
     * The type Builder.
     */
    public static final class Builder {

        private AppModule appModule;

        private UtilsModule utilsModule;

        private PresenterModule presenterModule;

        private Builder() {

        }

        /**
         * Build manager component.
         *
         * @return the manager component
         */
        public ManagerComponent build() {
            if (appModule == null)
                appModule = new AppModule();

            if (utilsModule == null)
                utilsModule = new UtilsModule();

            if (presenterModule == null)
                presenterModule = new PresenterModule();

            return new DaggerApiComponent(this);
        }

        /**
         * App module builder.
         *
         * @param appModule the app module
         * @return the builder
         */
        public Builder appModule(AppModule appModule) {
            this.appModule = Preconditions.checkNotNull(appModule);
            return this;
        }

        /**
         * Utils module builder.
         *
         * @param utilsModule the utils module
         * @return the builder
         */
        public Builder utilsModule(UtilsModule utilsModule) {
            this.utilsModule = Preconditions.checkNotNull(utilsModule);
            return this;
        }

        /**
         * Presenter module builder.
         *
         * @param presenterModule the presenter module
         * @return the builder
         */
        public Builder presenterModule(PresenterModule presenterModule) {
            this.presenterModule = Preconditions.checkNotNull(presenterModule);
            return this;
        }
    }
}
