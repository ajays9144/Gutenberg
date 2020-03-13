package com.igniteso.gutenberg.ui.feature.result;

import com.igniteso.gutenberg.model.BaseResponse;
import com.igniteso.gutenberg.model.BookResult;
import com.igniteso.gutenberg.repository.ShowBookRepository;
import com.igniteso.gutenberg.ui.feature.base.BasePresenter;
import com.igniteso.gutenberg.utils.AppConstants;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * The type Show book presenter.
 */
public class ShowBookPresenter extends BasePresenter<ShowBookContract.View> implements ShowBookContract.Presenter {

    private final ShowBookRepository showBookRepository;

    /**
     * Instantiates a new Show book presenter.
     *
     * @param showBookRepository the show book repository
     */
    public ShowBookPresenter(ShowBookRepository showBookRepository) {
        this.showBookRepository = showBookRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void showBooks(HashMap<String, String> parse) {
        parse.put(AppConstants.U_MIME_TYPE, AppConstants.MIME_IMAGE);
        getCompositeDisposable().add(showBookRepository.getBooks(parse).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponse<List<BookResult>>>() {
                    @Override
                    public void onNext(BaseResponse<List<BookResult>> response) {
                        if (getView() != null) {
                            getView().showViewLoadingIndicator(false);
                            getView().showBooks(response.getResult());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView() != null) {
                            getView().showViewLoadingIndicator(true);
                            getView().getNextPageUrl(null);
                            getView().showError(e.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    protected void onStart() {
                        super.onStart();
                        if (getView() != null) {
                            getView().showViewLoadingIndicator(true);
                        }
                    }
                }));
    }

    @Override
    public void showMoreBooks(HashMap<String, String> parse) {
        parse.put(AppConstants.U_MIME_TYPE, AppConstants.MIME_IMAGE);
        getCompositeDisposable().add(showBookRepository.getBooks(parse).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponse<List<BookResult>>>() {
                    @Override
                    public void onNext(BaseResponse<List<BookResult>> response) {
                        if (getView() != null) {
                            getView().showStartMoreLoading(false);
                            getView().showMoreBooks(response.getResult());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView() != null) {
                            getView().getNextPageUrl(null);
                            getView().showStartMoreLoading(false);
                            getView().showError(e.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    protected void onStart() {
                        super.onStart();
                        if (getView() != null) {
                            getView().showStartMoreLoading(true);
                        }
                    }
                }));

    }
}
