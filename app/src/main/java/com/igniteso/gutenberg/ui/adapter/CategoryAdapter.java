package com.igniteso.gutenberg.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.igniteso.gutenberg.R;
import com.igniteso.gutenberg.model.Category;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The type Category adapter.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {

    private Context context;
    private Callback callback;
    private List<Category> categories;

    /**
     * Instantiates a new Category adapter.
     *
     * @param context    the context
     * @param categories the categories
     */
    public CategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryHolder(LayoutInflater.from(context).inflate(R.layout.item_category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        try {
            Category category = categories.get(position);

            holder.mCategory.setImageResource(category.getCategoryImage());
            holder.mCategoryName.setText(category.getCategoryName());
        } catch (Exception e) {
            Log.e("Exception: ", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    /**
     * Sets callback.
     *
     * @param callback the callback
     */
    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    /**
     * The type Category holder.
     */
    class CategoryHolder extends RecyclerView.ViewHolder {

        /**
         * The M category.
         */
        @BindView(R.id.img_category)
        ImageView mCategory;
        /**
         * The M category name.
         */
        @BindView(R.id.txt_category_name)
        TextView mCategoryName;

        /**
         * Instantiates a new Category holder.
         *
         * @param itemView the item view
         */
        CategoryHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null)
                        callback.onPositionSelect(getAdapterPosition());
                }
            });
        }
    }
}
