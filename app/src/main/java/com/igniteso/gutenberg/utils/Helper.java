package com.igniteso.gutenberg.utils;

import android.content.Context;

import com.igniteso.gutenberg.R;
import com.igniteso.gutenberg.model.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Helper.
 */
public class Helper {

    /**
     * Gets book category.
     *
     * @param context the context
     * @return the book category
     */
    public static List<Category> getBookCategory(Context context) {
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category(1, context.getString(R.string.title_fiction), R.drawable.ic_fiction));
        categories.add(new Category(2, context.getString(R.string.title_drama), R.drawable.ic_drama));
        categories.add(new Category(3, context.getString(R.string.title_humor), R.drawable.ic_humour));
        categories.add(new Category(4, context.getString(R.string.title_politics), R.drawable.ic_politics));
        categories.add(new Category(5, context.getString(R.string.title_philosophy), R.drawable.ic_philosophy));
        categories.add(new Category(6, context.getString(R.string.title_history), R.drawable.ic_history));
        categories.add(new Category(7, context.getString(R.string.title_adventure), R.drawable.ic_adventure));
        return categories;
    }



}
