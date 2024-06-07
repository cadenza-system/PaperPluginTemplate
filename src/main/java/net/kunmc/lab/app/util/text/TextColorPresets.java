package net.kunmc.lab.app.util.text;

import net.kyori.adventure.text.format.TextColor;

public enum TextColorPresets {
    /**
     * ブラック
     */
    BLACK("§0", 0, 0, 0),
    /**
     * ダークブルー
     */
    DARK_BLUE("§1", 0, 0, 170),
    /**
     * ダークグリーン
     */
    DARK_GREEN("§2", 0, 170, 0),
    /**
     * ダークアクア
     */
    DARK_AQUA("§3", 0, 170, 170),
    /**
     * ダークレッド
     */
    DARK_RED("§4", 170, 0, 0),
    /**
     * ダークパープル
     */
    DARK_PURPLE("§5", 170, 0, 170),
    /**
     * ゴールド
     */
    GOLD("§6", 255, 170, 0),
    /**
     * グレー
     */
    GRAY("§7", 170, 170, 170),
    /**
     * ダークグレー
     */
    DARK_GRAY("§8", 85, 85, 85),
    /**
     * ブルー
     */
    BLUE("§9", 85, 85, 255),
    /**
     * グリーン
     */
    GREEN("§a", 85, 255, 85),
    /**
     * アクア
     */
    AQUA("§b", 85, 255, 255),
    /**
     * レッド
     */
    RED("§c", 255, 85, 85),
    /**
     * ライトパープル
     */
    LIGHT_PURPLE("§d", 255, 85, 255),
    /**
     * イエロー
     */
    YELLOW("§e", 255, 255, 85),
    /**
     * ホワイト
     */
    WHITE("§f", 255, 255, 255),
    /**
     * minecoin_golg
     */
    MINECOIN_GOLD("§g", 221, 214, 5);

    private final String code;
    private final int r;
    private final int g;
    private final int b;

    TextColorPresets(String code, int r, int g, int b) {
        this.code = code;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public String Prepend(String text) {
        return this.code + text;
    }

    public TextColor component() {
        return TextColor.color(this.r, this.g, this.b);
    }
}
