package view;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

/**
 * Represents a custom JTextField component with placeholder (shadow) text.
 */
public class PlaceholderTextField extends JTextField implements FocusListener {
  private final String placeholder;
  private boolean showingPlaceholder;

  public PlaceholderTextField(String placeholder) {
    super(placeholder);
    this.placeholder = placeholder;
    this.showingPlaceholder = true;
    addFocusListener(this);
  }

  public String getPlaceholderText() {
    return placeholder;
  }

  @Override
  public void focusGained(FocusEvent e) {
    if (this.showingPlaceholder) {
      this.setText("");
      this.showingPlaceholder = false;
    }
  }

  @Override
  public void focusLost(FocusEvent e) {
    if (this.getText().isEmpty()) {
      this.setText(placeholder);
      this.showingPlaceholder = true;
    }
  }
}

