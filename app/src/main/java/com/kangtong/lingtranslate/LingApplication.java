package com.kangtong.lingtranslate;

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

  @Override public void onCreate() {
    super.onCreate();

    initDB();
  }

  private void initDB() {
    SQLiteDatabase db = LitePal.getDatabase();
  }
}
