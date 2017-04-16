package com.kangtong.lingtranslate;

import android.database.sqlite.SQLiteDatabase;
import com.kangtong.lingtranslate.model.db.WordDB;
import java.util.List;
import org.litepal.LitePal;
import org.litepal.LitePalApplication;
import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.FindMultiCallback;

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

  /**
   * 下面这一行，会自行添加所有的，已映射的表
   */
  private void initDB() {
    SQLiteDatabase db = LitePal.getDatabase();
  }
}
