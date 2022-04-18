import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.FlowLayout;

public class GUI extends JFrame implements ActionListener {
    Multicast multicast;
    String message = "";
    public static JTextArea textArea = new JTextArea(18, 50);
    JButton connectionButton = new JButton("Connect");
    JPanel background = new JPanel();
    JPanel panel1 = new JPanel();
    JPanel settingsPanel = new JPanel();
    JScrollPane scrollPane = new JScrollPane(textArea);
    JTextField sendText = new JTextField(50);
    JScrollBar vertical = scrollPane.getVerticalScrollBar();

    JTextField nameField = new JTextField("Anonymous", 20);
    public static JTextField ipField = new JTextField(22);
    public static JTextField portField = new JTextField(22);
    public static JTextField interfaceField = new JTextField(22);
    JLabel nameLabel = new JLabel("Name");
    JLabel ipLabel = new JLabel("IP-address");
    JLabel portLabel = new JLabel("Port");
    JLabel interfaceLabel = new JLabel("Network interface");

    Listen listen;

    public GUI(Multicast multicast, Listen listen)
            throws UnknownHostException, SocketException, IOException {
        this.multicast = multicast;
        this.listen = listen;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(580, 450);
        setLocationRelativeTo(null);
        setVisible(true);

        add(background);
        background.setLayout(new FlowLayout());

        background.add(settingsPanel);
        settingsPanel.setLayout(new GridLayout(4, 2));
        settingsPanel.add(nameLabel);
        settingsPanel.add(nameField);
        settingsPanel.add(ipLabel);
        settingsPanel.add(ipField);
        settingsPanel.add(portLabel);
        settingsPanel.add(portField);
        settingsPanel.add(interfaceLabel);
        settingsPanel.add(interfaceField);

        background.add(connectionButton);
        connectionButton.addActionListener(this);

        background.add(panel1);
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.PAGE_AXIS));
        panel1.add(scrollPane);
        panel1.add(sendText);
        sendText.setEditable(false);
        textArea.setEditable(false);

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
                textArea.setEditable(false);
                multicast.leave();
                listen.interrupt();
                multicast = null;
                sendText.removeActionListener(this);
                nameField.setEditable(true);
                ipField.setEditable(true);
                portField.setEditable(true);
                interfaceField.setEditable(true);
            } else {
                try {
                    multicast = new Multicast();
                    listen = new Listen(multicast);
                    listen.start();
                    sendText.addActionListener(this);
                    connectionButton.setText("Disconnect");
                    sendText.setEditable(true);
                    textArea.setEditable(true);
                    nameField.setEditable(false);
                    ipField.setEditable(false);
                    portField.setEditable(false);
                    interfaceField.setEditable(false);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}