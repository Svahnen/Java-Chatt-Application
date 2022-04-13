import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

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
    public static JTextArea textArea = new JTextArea(20, 50);
    JPanel panel1 = new JPanel();
    JScrollPane scrollPane = new JScrollPane(textArea);
    JTextField sendText = new JTextField(50);
    JScrollBar vertical = scrollPane.getVerticalScrollBar();
    Listen listen;

    public GUI(Multicast multicast)
            throws UnknownHostException, SocketException, IOException {
        this.multicast = multicast;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(580, 380);
        setLocationRelativeTo(null);
        setVisible(true);

        add(panel1);
        panel1.add(scrollPane);
        panel1.add(sendText);
        sendText.addActionListener(this);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sendText) {
            if (!sendText.getText().equals("")) {
                try {
                    System.out.println("Send text: " + sendText.getText());
                    multicast.sendMessage(sendText.getText());
                    sendText.setText("");
                    vertical.setValue(vertical.getMaximum());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}