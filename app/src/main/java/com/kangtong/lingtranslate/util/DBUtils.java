package com.kangtong.lingtranslate.util;

import com.kangtong.lingtranslate.model.db.WordDB;
import org.litepal.crud.DataSupport;

/**
 * 作者：Create on 2017/4/16 11:37  by dq_dana
 * 邮箱：dqdanavera@gmail.com
 * 描述：对于数据库的处理
 */
public class DBUtils {

  /**
   * 添加至本地数据库
   *
   * @param bean ：欲添加的新数据
   * @return ： 是否成功
   */
  public static boolean insertIntoNote(WordDB bean) {
    return bean.save();
  }

  /**
   * 从本地数据库删除指定数据
   *
   * @param id ：欲删除的新数据
   * @return ： 是否成功
   */
  public static boolean deleteFromNote(int id) {
    return (DataSupport.delete(WordDB.class, id) != 0);
  }

  public static int deleteAll() {
    return (DataSupport.deleteAll(WordDB.class));
  }
}
