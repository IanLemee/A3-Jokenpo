import java.io.*;
import java.net.*;
import java.util.Scanner;

public class JokenpoClient {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Uso: java JokenpoClient <server address> <server port>");
            return;
        }

        String serverAddress = args[0];
        int serverPort = Integer.parseInt(args[1]);

        try (Socket socket = new Socket(serverAddress, serverPort);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            Scanner input = new Scanner(System.in);

            System.out.println("Digite seu nome:");
            String playerName = input.nextLine();
            out.println(playerName);

            for (int i = 0; i < 3; i++) {
                System.out.println("Suas opções: 1 - Pedra, 2 - Papel, 3 - Tesoura");
                int playerChoice = input.nextInt();
                out.println(playerChoice);

                String response = in.readLine();
                System.out.println(response);

                response = in.readLine();
                System.out.println(response);
            }

            String finalResult = in.readLine();
            System.out.println(finalResult);

            input.close();
        } catch (IOException e) {
            System.out.println("Erro na comunicação: " + e.getMessage());
        }
    }
}
