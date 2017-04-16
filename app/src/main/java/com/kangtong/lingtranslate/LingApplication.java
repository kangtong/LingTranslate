package com.kangtong.lingtranslate;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import org.litepal.LitePal;
import org.litepal.LitePalApplication;

/**
 * 作者：Create on 2017/4/16 00:42  by dq_dana
 * 邮箱：dqdanavera@gmail.com
 * 描述：用以初始化 LitePal
 */
public class LingApplication extends LitePalApplication {

  // 这里只是继承了 LitePalApplication， 什么都不用写， 如果需要初始化其他东西，再自行添加

  private static volatile Context sAppContext;

  @Override public void onCreate() {
    super.onCreate();
    initialize();
    initDB();
  }

  private void initialize() {
    sAppContext = getApplicationContext();
  }

  /**
   * 下面这一行，会自行添加所有的，已映射的表
   */
  private void initDB() {
    SQLiteDatabase db = LitePal.getDatabase();
  }

  public static Resources appResources() {
    return appContext().getResources();
  }

  public static Context appContext() {
    return sAppContext;
  }
}
