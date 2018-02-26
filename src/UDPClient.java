import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by poltorres0999 on 12/02/2018.
 */

public class UDPClient {

    private static final int ConnectionTries = 15;


    private DatagramSocket socket;
    private String ip_address;
    private int  port;
    private String response;

    private byte[] buffer;


    public UDPClient (String ip_address, int port) {
        this.ip_address = ip_address;
        this.port = port;
    }

    public boolean StartConnection()  {

        int tries = 0;
        boolean connected = false;

        try {

            this.socket = new DatagramSocket();

            InetAddress address = InetAddress.getByName(this.ip_address);
            this.buffer = "Request connection".getBytes();
            DatagramPacket ConnectionRequest = new DatagramPacket(this.buffer, this.buffer.length,
                    address, this.port);

            socket.send(ConnectionRequest);

            DatagramPacket ServerResponse = new DatagramPacket(this.buffer, this.buffer.length);
            this.socket.receive(ServerResponse);

            while(!connected && tries < ConnectionTries) {

                if (new String(ServerResponse.getData(), 0, ServerResponse.getLength())
                        .equals("Connection accepted")) {
                    connected = true;
                    this.response = "Connection accepted!";
                }
                tries++;
            }

        } catch (SocketException e) {
            e.printStackTrace();
            this.response = "Socket Exception: " + e.getMessage();

        } catch (UnknownHostException e) {
            e.printStackTrace();
            this.response = "Unknown Host Exception: " + e.getMessage();

        } catch (IOException e) {
            e.printStackTrace();
            this.response = "IOException: " + e.getMessage();

        }

        this.response = "Connection Error: Server refused connection!";
        return connected;
    }

    public void EndConnection () {

        this.socket.close();
    }

}

