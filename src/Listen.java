import java.io.IOException;

public class Listen extends Thread {
    Multicast multicast;

    public Listen(Multicast multicast) {
        System.out.println("Listen started");
        this.multicast = multicast;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                GUI.textArea.append(multicast.getData() + "\n");
                GUI.textArea.setCaretPosition(GUI.textArea.getDocument().getLength());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (Thread.interrupted()) {
            System.out.println("Listen stopped");
            multicast.close();
            multicast = null;
        }
    }
}