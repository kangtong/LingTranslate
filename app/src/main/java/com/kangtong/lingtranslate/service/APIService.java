package com.kangtong.lingtranslate.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kangt on 2017/4/13.
 */

public class APIService {
  public static YoudaoService youdaoService() {
    return youdaoRetrofit().create(YoudaoService.class);
  }

  private static Retrofit youdaoRetrofit() {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("http://fanyi.youdao.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    return retrofit;
  }
}
