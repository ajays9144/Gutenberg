package com.igniteso.gutenberg.ui.feature.result;

import com.igniteso.gutenberg.model.BookResult;
import com.igniteso.gutenberg.ui.feature.base.Contract;

import java.util.HashMap;
import java.util.List;

public abstract class ShowBookContract {

    public interface Presenter extends Contract.Presenter<View> {

        void showBooks(HashMap<String, String> parse);

        void showMoreBooks(HashMap<String, String> parse);
    }

    public interface View extends Contract.View {

        void showStartMoreLoading(boolean state);

        void getTotalItemCount(int totalCount);

        void getNextPageUrl(String nextPageUrl);

        void showViewLoadingIndicator(boolean state);

        void showBooks(List<BookResult> showBooksResults);

        void showMoreBooks(List<BookResult> showBooksResults);
    }
}
