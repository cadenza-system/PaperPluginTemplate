package net.kunmc.lab.paperplugintemplate.util.text;

public enum Font {
  /**
   * ブラック
   */
  BLACK("§0"),
  /**
   * ダークブルー
   */
  DARK_BLUE("§1"),
  /**
   * ダークグリーン
   */
  DARK_GREEN("§2"),
  /**
   * ダークアクア
   */
  DARK_AQUA("§3"),
  /**
   * ダークレッド
   */
  DARK_RED("§4"),
  /**
   * ダークパープル
   */
  DARK_PURPLE("§5"),
  /**
   * ゴールド
   */
  GOLD("§6"),
  /**
   * グレー
   */
  GRAY("§7"),
  /**
   * ダークグレー
   */
  DARK_GRAY("§8"),
  /**
   * ブルー
   */
  BLUE("§9"),
  /**
   * グリーン
   */
  GREEN("§a"),
  /**
   * アクア
   */
  AQUA("§b"),
  /**
   * レッド
   */
  RED("§c"),
  /**
   * ライトパープル
   */
  LIGHT_PURPLE("§d"),
  /**
   * イエロー
   */
  YELLOW("§e"),
  /**
   * ホワイト
   */
  WHITE("§f"),

  /**
   * 装飾コード
   */
  /**
   * 難読化
   */
  OBFUSCATED("§k"),
  /**
   * ボールド
   */
  BOLD("§l"),
  /**
   * 取り消し線
   */
  STRIKETHROUGH("§m"),
  /**
   * 下線
   */
  UNDERLINE("§n"),
  /**
   * イタリック
   */
  ITALIC("§o"),
  /**
   * リセット
   */
  RESET("§r");

  public String code;

  Font(String code) {
    this.code = code;
  }

  public Text set(Text text) {
    return text.prepend(this.code);
  }

  public Text set(String text) {
    return new Text(text).prepend(this.code);
  }
}
