public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Receiver started");
        MulticastSender sender = new MulticastSender();
        MulticastReceiver receiver = new MulticastReceiver();
        GUI giu = new GUI(receiver, sender);

    }
}