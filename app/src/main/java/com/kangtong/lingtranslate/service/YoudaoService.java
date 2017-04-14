package com.kangtong.lingtranslate.service;

import com.kangtong.lingtranslate.model.YoudaoResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by kangt on 2017/4/13.
 */

public interface YoudaoService {
  @GET("openapi.do") Call<YoudaoResult> getYoudao(@Query("keyfrom") String keyfrom,
      @Query("key") String key, @Query("type") String type, @Query("doctype") String doctype,
      @Query("version") String version, @Query("q") String q);
}
