import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UDP_Server {

    public static void main(String[] args) {
        try {
            DatagramSocket datagramSocket = new DatagramSocket(15271);
            while (true) {
                new UDP_Server().serverWork(datagramSocket);
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }
    public void serverWork(DatagramSocket datagramSocket) {
        try {
            byte[] inputBuffer = new byte[1024];
            byte[] outputBuffer;
            DatagramPacket packet = new DatagramPacket(inputBuffer,inputBuffer.length);
            datagramSocket.receive(packet);
            String clientMessage = new String(packet.getData(),0,packet.getLength());
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            System.out.println("Сервер принял сообщение от клиента. Время получения сообщения: " +
                    simpleDateFormat.format(date) + ". Содержание сообщения: " +
                    clientMessage + ". Адрес клиента: " +
                    packet.getAddress());
            outputBuffer = simpleDateFormat.format(date).getBytes();
            DatagramPacket outPacket = new DatagramPacket(outputBuffer,outputBuffer.length, packet.getAddress(), packet.getPort());
            datagramSocket.send(outPacket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
