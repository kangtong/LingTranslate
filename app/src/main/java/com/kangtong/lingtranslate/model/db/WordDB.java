package com.kangtong.lingtranslate.model.db;

import java.util.UUID;
import org.litepal.annotation.Column;

/**
 * 作者：Create on 2017/4/16 00:48  by dq_dana
 * 邮箱：dqdanavera@gmail.com
 * 描述：单词的表映射实体
 */
public class WordDB extends BaseDBBean {

  public static final String KEY_BAIDU = "baidu";
  public static final String KEY_YOUDAO = "youdao";
  public static final String KEY_JINSHAN = "jinshan";

  // 我的设计如下： 源 - 源语种 - 结果 - 结果语种 - 翻译工具类型
  @Column(unique = true, defaultValue = "unknown")
  public UUID id;
  public String src;
  public String srcLan;
  public String dst;
  public String dstLan;
  public String type;

  public WordDB(String src, String srcLan, String dst, String dstLan, String type) {
    this.id = UUID.randomUUID();
    this.src = src;
    this.srcLan = srcLan;
    this.dst = dst;
    this.dstLan = dstLan;
    this.type = type;
  }

  public UUID getId() {
    return id;
  }

  public String getSrc() {
    return src;
  }

  public void setSrc(String src) {
    this.src = src;
  }

  public String getSrcLan() {
    return srcLan;
  }

  public void setSrcLan(String srcLan) {
    this.srcLan = srcLan;
  }

  public String getDst() {
    return dst;
  }

  public void setDst(String dst) {
    this.dst = dst;
  }

  public String getDstLan() {
    return dstLan;
  }

  public void setDstLan(String dstLan) {
    this.dstLan = dstLan;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override public String toString() {
    return "WordDB{" +
        "id=" + id +
        ", src='" + src + '\'' +
        ", srcLan='" + srcLan + '\'' +
        ", dst='" + dst + '\'' +
        ", dstLan='" + dstLan + '\'' +
        ", type='" + type + '\'' +
        '}';
  }
}
