public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Receiver started");
        // MulticastSender sender = new MulticastSender();
        Multicast multicast = new Multicast();
        Listen listen = new Listen(multicast);
        GUI giu = new GUI(multicast);
        listen.start();

    }
}