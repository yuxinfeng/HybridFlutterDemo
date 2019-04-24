package com.hybirdflutter.network;

import com.hybirdflutter.model.MovieEntity;
import com.hybirdflutter.model.Subject;
import com.hybirdflutter.model.SubjectResult;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * author：yuxinfeng on 2019-04-24 15:05
 * email：yuxinfeng@corp.netease.com
 */
public interface MovieService {

//    @GET("top250")
//    Observable<MovieEntity> getTopMovie(@Query("start") int start, @Query("count") int count);
    @GET("top250")
    Observable<SubjectResult<List<Subject>>> getTopMovie(@Query("start") int start, @Query("count") int count);

}
