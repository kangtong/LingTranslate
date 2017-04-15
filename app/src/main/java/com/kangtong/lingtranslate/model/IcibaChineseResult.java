package com.kangtong.lingtranslate.model;

import java.util.List;

/**
 * Created by kangt on 2017/4/15.
 */

public class IcibaChineseResult extends IcibaResult {

  public List<SymbolsBean> symbols;

  public static class SymbolsBean {
    /**
     * word_symbol : xiào symbol_mp3 : http://res.iciba.com/hanyu/zi/62c78c401660acf646e9fa97e67f23f2.mp3
     * parts : [{"part_name":"动","means":[{"word_mean":"smile","has_mean":"1","split":1},{"word_mean":"laugh","has_mean":"1","split":1},{"word_mean":"ridicule","has_mean":"1","split":1},{"word_mean":"laugh
     * at","has_mean":"1","split":1},{"word_mean":"laughter ","has_mean":"1","split":0}]}] ph_am_mp3
     * : ph_en_mp3 : ph_tts_mp3 : ph_other :
     */

    public String word_symbol;
    public String symbol_mp3;
    public String ph_am_mp3;
    public String ph_en_mp3;
    public String ph_tts_mp3;
    public String ph_other;
    public List<PartsBean> parts;

    public static class PartsBean {
      /**
       * part_name : 动 means : [{"word_mean":"smile","has_mean":"1","split":1},{"word_mean":"laugh","has_mean":"1","split":1},{"word_mean":"ridicule","has_mean":"1","split":1},{"word_mean":"laugh
       * at","has_mean":"1","split":1},{"word_mean":"laughter ","has_mean":"1","split":0}]
       */

      public String part_name;
      public List<MeansBean> means;

      public static class MeansBean {
        /**
         * word_mean : smile
         * has_mean : 1
         * split : 1
         */

        public String word_mean;
        public String has_mean;
        public int split;
      }
    }
  }
}
