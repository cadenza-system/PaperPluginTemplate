package net.kunmc.lab.app.util.text;

import net.kyori.adventure.text.format.TextDecoration;

public enum TextDecolation {

    /**
     * 装飾コード
     */
    /**
     * 難読化
     */
    OBFUSCATED("§k", TextDecoration.OBFUSCATED),
    /**
     * ボールド
     */
    BOLD("§l", TextDecoration.BOLD),
    /**
     * 取り消し線
     */
    STRIKETHROUGH("§m", TextDecoration.STRIKETHROUGH),
    /**
     * 下線
     */
    UNDERLINE("§n", TextDecoration.UNDERLINED),
    /**
     * イタリック
     */
    ITALIC("§o", TextDecoration.ITALIC),
    /**
     * リセット
     */
    RESET("§r", TextDecoration.ITALIC);

    public String code;
    public TextDecoration component;

    TextDecolation(String code, TextDecoration component) {
        this.code = code;
        this.component = component;
    }

    public String prepend(String text) {
        return this.code + text;
    }

    public TextDecoration component() {
        return this.component;
    }
}
