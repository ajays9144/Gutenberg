package com.igniteso.gutenberg.network;

import com.igniteso.gutenberg.model.BaseResponse;
import com.igniteso.gutenberg.model.BookResult;
import com.igniteso.gutenberg.utils.AppConstants;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * The interface Api call interface.
 */
public interface ApiCallInterface {

    /**
     * Gets books.
     *
     * @param parse the parse
     * @return the books
     */
    @GET(AppConstants.U_BOOKS)
    Observable<BaseResponse<List<BookResult>>> getBooks(@QueryMap HashMap<String, String> parse);
}
