import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI extends JFrame implements ActionListener {
    Multicast multicast;
    String message = "";
    public static JTextArea textArea = new JTextArea(18, 50);
    JTextField nameField = new JTextField("Anonymous", 20);
    JButton connectionButton = new JButton("Disconnect");
    JPanel panel1 = new JPanel();
    JScrollPane scrollPane = new JScrollPane(textArea);
    JTextField sendText = new JTextField(50);
    JScrollBar vertical = scrollPane.getVerticalScrollBar();
    Listen listen;

    public GUI(Multicast multicast, Listen listen)
            throws UnknownHostException, SocketException, IOException {
        this.multicast = multicast;
        this.listen = listen;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(580, 380);
        setLocationRelativeTo(null);
        setVisible(true);

        add(panel1);
        panel1.add(nameField);
        panel1.add(connectionButton);
        connectionButton.addActionListener(this);
        panel1.add(scrollPane);
        panel1.add(sendText);
        sendText.addActionListener(this);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sendText) {
            if (!sendText.getText().equals("")) {
                try {
                    System.out.println("Send text: " + sendText.getText());
                    multicast.sendMessage(nameField.getText() + ": " + sendText.getText());
                    sendText.setText("");
                    vertical.setValue(vertical.getMaximum());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } else if (e.getSource() == connectionButton) {
            if (connectionButton.getText().equals("Disconnect")) {
                connectionButton.setText("Connect");
                System.out.println("Disconnect button pressed");
                sendText.setEditable(false);
                multicast.leave();
                listen.interrupt();
                multicast = null;
                sendText.removeActionListener(this);
            } else {
                try {
                    multicast = new Multicast();
                    listen = new Listen(multicast);
                    listen.start();
                    sendText.addActionListener(this);
                    connectionButton.setText("Disconnect");
                    sendText.setEditable(true);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}