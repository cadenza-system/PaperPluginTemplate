package net.kunmc.lab.paperplugintemplate.util.text;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

public class Text {

  private String value;

  public Text(String text) {
    this.value = text;
  }

  public Text(Component component) {
    this.value = ((TextComponent) component).content();
  }

  public Text(TextComponent textComponent) {
    this.value = textComponent.content();
  }

  public String toString() {
    return this.value;
  }

  public Text prepend(String text) {
    this.value = text + this.value;
    return this;
  }

  public Text prepend(Text text) {
    return prepend(text.value);
  }

  public Text append(String text) {
    this.value = this.value + text;
    return this;
  }

  public Text append(Text text) {
    return append(text.value);
  }
}
