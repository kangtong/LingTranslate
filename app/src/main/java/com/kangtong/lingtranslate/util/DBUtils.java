package com.kangtong.lingtranslate.util;

import com.kangtong.lingtranslate.model.db.WordDB;

/**
 * 作者：Create on 2017/4/16 11:37  by dq_dana
 * 邮箱：dqdanavera@gmail.com
 * 描述：对于数据库的处理
 */
public class DBUtils {

  /**
   * 收藏：添加至本地数据库
   *
   * @param bean ：源数据
   * @return ： 是否成功
   */
  public static boolean addIntoNote(WordDB bean) {
    return bean.save();
  }
}
