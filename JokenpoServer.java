import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class JokenpoServer {
    private static ExecutorService pool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Uso: java JokenpoServer <port>");
            return;
        }

        int port = Integer.parseInt(args[0]);
        ServerSocket listener = new ServerSocket(port);
        System.out.println("Servidor de Jokenpo iniciado na porta " + port);

        try {
            while (true) {
                Socket player1 = listener.accept();
                Socket player2 = listener.accept();
                pool.execute(new GameHandler(player1, player2));
            }
        } finally {
            listener.close();
        }
    }

    private static class GameHandler implements Runnable {
        private Socket player1;
        private Socket player2;
        private BufferedReader in1;
        private BufferedReader in2;
        private PrintWriter out1;
        private PrintWriter out2;

        public GameHandler(Socket player1, Socket player2) throws IOException {
            this.player1 = player1;
            this.player2 = player2;
            in1 = new BufferedReader(new InputStreamReader(player1.getInputStream()));
            in2 = new BufferedReader(new InputStreamReader(player2.getInputStream()));
            out1 = new PrintWriter(player1.getOutputStream(), true);
            out2 = new PrintWriter(player2.getOutputStream(), true);
        }

        @Override
        public void run() {
            try {
                String player1Name = in1.readLine();
                String player2Name = in2.readLine();

                int player1Wins = 0;
                int player2Wins = 0;
                int draws = 0;

                for (int i = 0; i < 3; i++) {
                    out1.println("Sua vez (1 - Pedra, 2 - Papel, 3 - Tesoura): ");
                    out2.println("Sua vez (1 - Pedra, 2 - Papel, 3 - Tesoura): ");

                    int player1Choice = Integer.parseInt(in1.readLine());
                    int player2Choice = Integer.parseInt(in2.readLine());

                    if (player1Choice == player2Choice) {
                        out1.println("Empate!");
                        out2.println("Empate!");
                        draws++;
                    } else if ((player1Choice == 1 && player2Choice == 3) || 
                               (player1Choice == 2 && player2Choice == 1) || 
                               (player1Choice == 3 && player2Choice == 2)) {
                        out1.println("Vitória!");
                        out2.println("Derrota!");
                        player1Wins++;
                    } else {
                        out1.println("Derrota!");
                        out2.println("Vitória!");
                        player2Wins++;
                    }
                }

                out1.println("Resultado Final: " + player1Wins + " Vitórias, " + player2Wins + " Derrotas, " + draws + " Empates.");
                out2.println("Resultado Final: " + player2Wins + " Vitórias, " + player1Wins + " Derrotas, " + draws + " Empates.");
            } catch (IOException e) {
                System.out.println("Erro no jogo: " + e.getMessage());
            } finally {
                try {
                    player1.close();
                    player2.close();
                } catch (IOException e) {
                    System.out.println("Erro ao fechar a conexão: " + e.getMessage());
                }
            }
        }
    }
}
