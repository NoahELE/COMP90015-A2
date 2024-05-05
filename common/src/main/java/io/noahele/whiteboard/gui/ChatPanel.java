// Xinhao Chen 1166113
package io.noahele.whiteboard.gui;

import io.noahele.whiteboard.Chat;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import javax.swing.*;

public class ChatPanel extends JPanel {
  private final Chat chat;
  private final JTextArea chatArea;
  private final JTextField inputField;

  public ChatPanel(Chat chat, String username) {
    this.chat = chat;
    setLayout(new BorderLayout());

    // create the chat area
    JLabel chatLabel = new JLabel("Chat Message");
    add(chatLabel, BorderLayout.NORTH);
    chatArea = new JTextArea();
    chatArea.setEditable(false);
    chatArea.setLineWrap(true);
    chatArea.setWrapStyleWord(true);
    JScrollPane scrollPane = new JScrollPane(chatArea);
    add(scrollPane, BorderLayout.CENTER);

    // create the input field
    inputField = new JTextField();
    inputField.addKeyListener(
        new KeyAdapter() {
          @Override
          public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
              String text = inputField.getText();
              if (!text.isEmpty()) {
                inputField.setText("");
                try {
                  chat.addMessage(username, text);
                } catch (RemoteException ex) {
                  throw new RuntimeException(ex);
                }
              }
            }
          }
        });
    add(inputField, BorderLayout.SOUTH);
  }

  /** Update chat text message. */
  public void updateChat() {
    StringBuilder sb = new StringBuilder();
    try {
      for (int i = 0; i < chat.getMessages().size(); i++) {
        sb.append(chat.getUsernames().get(i));
        sb.append(": ");
        sb.append(chat.getMessages().get(i));
        sb.append("\n");
      }
    } catch (RemoteException e) {
      throw new RuntimeException(e);
    }
    chatArea.setText(sb.toString());
  }
}
