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

import com.bumptech.glide.Glide;
import com.igniteso.gutenberg.R;
import com.igniteso.gutenberg.model.Author;
import com.igniteso.gutenberg.model.BookResult;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The type Show book result adapter.
 */
public class ShowBookResultAdapter extends RecyclerView.Adapter<ShowBookResultAdapter.ShowBookViewHolder> {

    private Context context;
    private List<BookResult> bookResults;
    private Callback callback;

    /**
     * Instantiates a new Show book result adapter.
     *
     * @param context     the context
     * @param bookResults the book results
     */
    public ShowBookResultAdapter(Context context, List<BookResult> bookResults) {
        this.context = context;
        this.bookResults = bookResults;
    }

    /**
     * Swap list.
     *
     * @param list the list
     */
    public void swapList(List<BookResult> list) {
        if (list != null && !list.isEmpty()) {
            bookResults.clear();
            bookResults.addAll(list);
        }
        notifyDataSetChanged();
    }

    /**
     * Add more books.
     *
     * @param list the list
     */
    public void addMoreBooks(List<BookResult> list) {
        if (list != null) {
            bookResults.addAll(list);
        }
        notifyDataSetChanged();
    }

    /**
     * Sets callback.
     *
     * @param callback the callback
     */
    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public ShowBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ShowBookViewHolder(LayoutInflater.from(context).inflate(R.layout.item_book_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShowBookViewHolder holder, int position) {
        try {
            BookResult bookResult = bookResults.get(position);

            Glide.with(context).load(bookResult.getFormats().getImageJped()).placeholder(R.drawable.ic_cover_book)
                    .error(R.drawable.ic_cover_book).into(holder.mCover);
            holder.mBookName.setText(bookResult.getTitle());
            holder.mAuthorName.setText(authorName(bookResult.getAuthors()));
        } catch (Exception e) {
            Log.e("Exception: ", e.getMessage());
        }
    }

    private String authorName(List<Author> authors) {
        StringBuilder builder = new StringBuilder();
        for (Author author : authors) {
            builder.append(author.getName()).append(", ");
        }
        return builder.toString().substring(0, builder.length() - 2);
    }

    @Override
    public int getItemCount() {
        return bookResults.size();
    }

    /**
     * The type Show book view holder.
     */
    class ShowBookViewHolder extends RecyclerView.ViewHolder {

        /**
         * The M cover.
         */
        @BindView(R.id.img_cover)
        ImageView mCover;
        /**
         * The M book name.
         */
        @BindView(R.id.txt_book_name)
        TextView mBookName;
        /**
         * The M author name.
         */
        @BindView(R.id.txt_author_name)
        TextView mAuthorName;

        /**
         * Instantiates a new Show book view holder.
         *
         * @param itemView the item view
         */
        ShowBookViewHolder(@NonNull View itemView) {
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
