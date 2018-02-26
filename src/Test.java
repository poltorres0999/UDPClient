
public class Test {

    public static void main (String[] args ) {
        UDPClient UdpClient = new UDPClient("X",1234);

        if (!UdpClient.StartConnection()) {
            System.out.println("Connection refused!");
            System.exit(-1);
        }

        System.out.print("Closing client socket");
        UdpClient.EndConnection();
        System.out.print("Closed socket");
    }
}
