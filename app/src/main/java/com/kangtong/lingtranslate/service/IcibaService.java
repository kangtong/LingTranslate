package com.kangtong.lingtranslate.service;

import com.kangtong.lingtranslate.model.IcibaChineseResult;
import com.kangtong.lingtranslate.model.IcibaEnglishResult;
import com.kangtong.lingtranslate.model.IcibaResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by kangt on 2017/4/15.
 */

public interface IcibaService {
  @GET("dictionary.php") Call<IcibaResult> getIciba(
      @Query("w") String w,
      @Query("key") String key,
      @Query("type") String type
  );

  @GET("dictionary.php") Call<IcibaChineseResult> getChineseIciba(
      @Query("w") String w,
      @Query("key") String key,
      @Query("type") String type
  );

  @GET("dictionary.php") Call<IcibaEnglishResult> getEnglishIciba(
      @Query("w") String w,
      @Query("key") String key,
      @Query("type") String type
  );
}
