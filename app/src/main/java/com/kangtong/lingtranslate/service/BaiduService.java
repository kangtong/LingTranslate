package com.kangtong.lingtranslate.service;

import com.kangtong.lingtranslate.model.BaiduResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by kangt on 2017/4/15.
 */

public interface BaiduService {
  @GET("translate") Call<BaiduResult> getBaidu(
      @Query("q") String q,
      @Query("from") String from,
      @Query("to") String to,
      @Query("appid") String appid,
      @Query("salt") String salt,
      @Query("sign") String sign
  );
}
