package com.igniteso.gutenberg.ui.feature.category;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.igniteso.gutenberg.R;
import com.igniteso.gutenberg.ui.adapter.Callback;
import com.igniteso.gutenberg.ui.adapter.CategoryAdapter;
import com.igniteso.gutenberg.ui.feature.result.ShowBookResultActivity;
import com.igniteso.gutenberg.model.Category;
import com.igniteso.gutenberg.utils.Helper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The type Book category activity.
 */
public class BookCategoryActivity extends AppCompatActivity implements Callback {

    @BindView(R.id.recycler_category)
    RecyclerView mRecyclerCategory;

    private ArrayList<Category> categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_category_activity);
        ButterKnife.bind(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        initCategories();
    }

    private void initCategories() {
        categories.addAll(Helper.getBookCategory(BookCategoryActivity.this));
        CategoryAdapter adapter = new CategoryAdapter(BookCategoryActivity.this, categories);
        adapter.setCallback(this);
        mRecyclerCategory.setAdapter(adapter);
    }

    @Override
    public void onPositionSelect(int position) {
        startActivity(ShowBookResultActivity.createIntent(BookCategoryActivity.this, categories.get(position).getCategoryName()));
    }
}
