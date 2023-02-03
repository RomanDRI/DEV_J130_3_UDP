import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;

public class UDP_Client {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите сообщение для сервера");
        String clientMessage = scanner.next();
        System.out.println("Введите адрес сервера");
        InetAddress inetAddress = InetAddress.getByName(scanner.next());
        try (DatagramSocket datagramSocket = new DatagramSocket()) {
            byte[] inputBuffer = new byte[1024];
            byte[] outputBuffer;
            outputBuffer = clientMessage.getBytes();
            DatagramPacket outPacket = new DatagramPacket(outputBuffer, outputBuffer.length, inetAddress, 15271);
            datagramSocket.send(outPacket);
            DatagramPacket inPacket = new DatagramPacket(inputBuffer, inputBuffer.length);
            datagramSocket.receive(inPacket);
            String serverMessage = new String(inPacket.getData(),0,inPacket.getLength());
            System.out.println("Сервер передал время обращения: " + serverMessage);
        } catch (SocketException e) {
            System.out.println("Соединение с сервером прекратилось. Попробуйте ещё раз.");
        }
    }
}
