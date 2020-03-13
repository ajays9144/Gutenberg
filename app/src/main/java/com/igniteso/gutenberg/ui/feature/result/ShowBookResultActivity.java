package com.igniteso.gutenberg.ui.feature.result;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.igniteso.gutenberg.R;
import com.igniteso.gutenberg.di.ManagerComponent;
import com.igniteso.gutenberg.model.BookResult;
import com.igniteso.gutenberg.ui.adapter.Callback;
import com.igniteso.gutenberg.ui.adapter.ShowBookResultAdapter;
import com.igniteso.gutenberg.ui.feature.base.BaseActivityViewModel;
import com.igniteso.gutenberg.ui.views.BaseRecyclerView;
import com.igniteso.gutenberg.utils.AppConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * The type Show book result activity.
 */
public class ShowBookResultActivity extends BaseActivityViewModel<ShowBookContract.Presenter> implements ShowBookContract.View, Callback {

    private final static String CATEGORY_NAME = "category_name";

    @BindView(R.id.search_container)
    LinearLayout mSearchContainer;
    @BindView(R.id.empty_text)
    TextView mMessage;
    @BindView(R.id.img_back)
    ImageView mBack;
    @BindView(R.id.txt_title)
    TextView mTitle;
    @BindView(R.id.edt_search)
    EditText mSearch;
    @BindView(R.id.img_cancel)
    ImageView mCancel;
    @BindView(R.id.show_books_recycler)
    BaseRecyclerView mShowBooks;
    @BindView(R.id.view_switcher)
    ViewSwitcher mSwitcher;

    /**
     * The Presenter.
     */
    @Inject
    ShowBookContract.Presenter presenter;

    private String categoryName;
    private GridLayoutManager gridLayoutManager;
    private int maxItemCount, currentPageCount = 1;
    private boolean isLoading, isLastPage;
    private ShowBookResultAdapter showBookResultAdapter;
    private HashMap<String, String> parse = new HashMap<>();
    private ArrayList<BookResult> bookResults = new ArrayList<>();

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (gridLayoutManager != null) {
                int visibleItemCount = gridLayoutManager.getChildCount();
                int totalItemCount = gridLayoutManager.getItemCount();
                int firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition();

                if (!isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItem) >= totalItemCount && firstVisibleItem >= 0 && totalItemCount >= maxItemCount) {
                        parse.put(AppConstants.U_PAGE, String.valueOf(currentPageCount));
                        getPresenter().showMoreBooks(parse);
                        currentPageCount++;
                    }
                }
            }
        }
    };

    /**
     * Create intent intent.
     *
     * @param context      the context
     * @param categoryName the category name
     * @return the intent
     */
    public static Intent createIntent(Context context, String categoryName) {
        return new Intent(context, ShowBookResultActivity.class).putExtra(CATEGORY_NAME, categoryName);
    }

    @Override
    public ShowBookContract.Presenter injectPresenter(ManagerComponent managerComponent) {
        managerComponent.inject(this);
        return presenter;
    }

    /**
     * On back.
     */
    @OnClick(R.id.img_back)
    public void onBack() {
        onBackPressed();
    }

    /**
     * On search book.
     *
     * @param s the s
     */
    @OnTextChanged(R.id.edt_search)
    public void onSearchBook(Editable s) {
        if (!s.toString().trim().isEmpty()) {
            currentPageCount = 1;
            parse.put(AppConstants.U_PAGE, String.valueOf(currentPageCount));
            parse.put(AppConstants.U_SEARCH, mSearch.getText().toString().trim());
            getPresenter().showBooks(parse);
        } else {
            if (parse.containsKey(AppConstants.U_SEARCH)) parse.remove(AppConstants.U_SEARCH);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_book_activity);
        ButterKnife.bind(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (getIntent() != null) {
            categoryName = getIntent().getStringExtra(CATEGORY_NAME);
            mTitle.setText(categoryName);
        }

        initRecycler();

        parse.put(AppConstants.U_TOPIC, categoryName);
        getPresenter().showBooks(parse);
    }

    /**
     * Init recycler.
     */
    public void initRecycler() {
        showBookResultAdapter = new ShowBookResultAdapter(ShowBookResultActivity.this, bookResults);
        mShowBooks.setAdapter(showBookResultAdapter);
        mShowBooks.setEmptyView(ShowBookResultActivity.this, mMessage, getString(R.string.msg_no_book_found));
        showBookResultAdapter.notifyDataSetChanged();
        showBookResultAdapter.setCallback(this);
        mShowBooks.addOnScrollListener(onScrollListener);
        gridLayoutManager = (GridLayoutManager) mShowBooks.getLayoutManager();
    }

    @Override
    public void showStartMoreLoading(boolean state) {
        isLoading = state;
    }

    @Override
    public void getTotalItemCount(int totalCount) {
        this.maxItemCount = totalCount;
    }

    @Override
    public void getNextPageUrl(String nextPageUrl) {
        if (nextPageUrl == null) isLastPage = true;
    }

    @Override
    public void showViewLoadingIndicator(boolean state) {
        mSwitcher.setDisplayedChild(state ? 0 : 1);
    }

    @Override
    public void showBooks(List<BookResult> showBooksResults) {
        showBookResultAdapter.swapList(showBooksResults);
    }

    @Override
    public void showMoreBooks(List<BookResult> showBooksResults) {
        showBookResultAdapter.addMoreBooks(showBooksResults);
        isLoading = false;
    }

    @Override
    public void showError(String error) {
        Toast.makeText(ShowBookResultActivity.this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPositionSelect(int position) {
        String htmlURL = bookResults.get(position).getFormats().getTextHtmlCharsetUtf8();
        String pdfURL = bookResults.get(position).getFormats().getApplicationPdf();
        String textURL = bookResults.get(position).getFormats().getTextPlainCharsetUtf8();
        String textAsciiURL = bookResults.get(position).getFormats().getTextPlainCharsetUsAscii();
        String zipURL = bookResults.get(position).getFormats().getApplicationZip();

        if (htmlURL != null && !htmlURL.contains("zip"))
            openBookAtWeb(htmlURL);
        else if (pdfURL != null && !pdfURL.contains("zip"))
            openBookAtWeb(pdfURL);
        else if (textURL != null && !textURL.contains("zip"))
            openBookAtWeb(textURL);
        else if (textAsciiURL != null && !textAsciiURL.contains("zip"))
            openBookAtWeb(textAsciiURL);
        else if (zipURL != null)
            showErrorAlertBox(R.string.txt_zip_viewable_available);
        else
            showErrorAlertBox(R.string.txt_viewable_available);
    }

    private void openBookAtWeb(String url) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    private void showErrorAlertBox(@StringRes int message) {
        new AlertDialog.Builder(ShowBookResultActivity.this)
                .setTitle(R.string.txt_error)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }
}
