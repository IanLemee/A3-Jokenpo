import java.util.Random;
import java.util.Scanner;

public class Jokenpo {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Escolha o modo de jogo: 1 - Maquina, 2 - Online");
        int gameMode = input.nextInt();
        input.nextLine();  

        if (gameMode == 1) {
            jogarContraBot(input);
        } else if (gameMode == 2) {
            System.out.println("Escolha o tipo de jogo online: 1 - Hostear jogo, 2 - Entrar em jogo");
            int onlineMode = input.nextInt();
            input.nextLine();  

            if (onlineMode == 1) {
                try {
                    JokenpoServer.main(new String[]{});
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (onlineMode == 2) {
                try {
                    JokenpoClient.main(new String[]{});
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Modo de jogo online inválido.");
            }
        } else {
            System.out.println("Modo de jogo inválido.");
        }

        input.close();
    }

    public static void jogarContraBot(Scanner input) {
        String[] escolhas = {"Pedra", "Papel", "Tesoura"};
        Random rand = new Random();

        System.out.println("Digite seu nome:");
        String playerName = input.nextLine();

        int playerWins = 0;
        int botWins = 0;

        for (int i = 0; i < 3; i++) {
            if (playerWins == 2 || botWins == 2) {
                break;
            }

            System.out.println("Suas opções: 1 - Pedra, 2 - Papel, 3 - Tesoura");
            int playerChoice = input.nextInt() - 1;
            int botChoice = rand.nextInt(3);

            System.out.println("Você escolheu: " + escolhas[playerChoice]);
            System.out.println("O bot escolheu: " + escolhas[botChoice]);

            if (playerChoice == botChoice) {
                System.out.println("Empate");
            } else if ((playerChoice == 0 && botChoice == 2) || 
                       (playerChoice == 1 && botChoice == 0) || 
                       (playerChoice == 2 && botChoice == 1)) {
                System.out.println("Vitória");
                playerWins++;
                System.out.println("O Player " + playerName + " venceu esta rodada!");
            } else {
                System.out.println("Derrota");
                botWins++;
                System.out.println("O Bot venceu esta rodada!");
            }
        }

        if (playerWins > botWins) {
            System.out.println("O Player " + playerName + " venceu o jogo!");
        } else {
            System.out.println("O Bot venceu o jogo!");
        }
    }
}
