package com.igniteso.gutenberg.repository;

import android.content.Context;

import com.igniteso.gutenberg.model.BaseResponse;
import com.igniteso.gutenberg.model.BookResult;
import com.igniteso.gutenberg.network.ApiCallInterface;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;

/**
 * The type Show book repository.
 */
public class ShowBookRepository extends BaseRepository {
    private final ApiCallInterface apiCallInterface;

    /**
     * Instantiates a new Show book repository.
     *
     * @param context          the context
     * @param apiCallInterface the api call interface
     */
    public ShowBookRepository(Context context, ApiCallInterface apiCallInterface) {
        super(context);
        this.apiCallInterface = apiCallInterface;
    }

    /**
     * Gets books.
     *
     * @param parse the parse
     * @return the books
     */
    public Observable<BaseResponse<List<BookResult>>> getBooks(HashMap<String, String> parse) {
        return apiCallInterface.getBooks(parse);
    }
}
