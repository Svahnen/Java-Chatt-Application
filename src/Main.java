public class Main {

    public static void main(String[] args) throws Exception {
        Multicast multicast = new Multicast();
        Listen listen = new Listen(multicast);
        listen.start();
        GUI giu = new GUI(multicast);

    }
}