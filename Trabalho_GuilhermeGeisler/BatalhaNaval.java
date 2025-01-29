/*Fiz duas implementações que não foram pedidas, 
 *eu adicionei um contador de rodadas e documentei no código
 *(Por Favor não retire pontos por causa disso).
 */

/*Outras Implementações que eu pensei porém não adicionei:
 * S Para sair do jogo, JOptionPane ao invés do Scanner,
 * Retirar os erros do tabuleiro e deixar só os navios ao acabar o jogo.
 * Utilizar várias classes, deixaria o código mais fácil de visualizar
 */

 /* Foi complicado arrumar o erro de entradas vazias sem utilizar Try Catch */

import java.util.Scanner;
import java.util.Random;

/**
 * Criação do jogo Batalha Naval em java.
 */
public class BatalhaNaval {

    /**
     * Constantes do jogo.
     */
    private static final int TAMANHO_TABULEIRO = 8;
    private static final int QUANTIDADE_NAVIOS = 10;
    private static final char AGUA = '~';
    private static final char NAVIO = 'N';
    private static final char TENTATIVA_AGUA = 'O';
    private static final char TENTATIVA_ACERTO = 'X';

    /**
     * Variáveis do jogo.
     */
    private static char[][] tabuleiro = new char[TAMANHO_TABULEIRO][TAMANHO_TABULEIRO];
    private static boolean[][] navios = new boolean[TAMANHO_TABULEIRO][TAMANHO_TABULEIRO];
    private static int naviosRestantes = QUANTIDADE_NAVIOS;
    private static int tentativas = 0;
    private static final int TENTATIVAS_MAXIMAS = 30;

    /**
     * Variáveis auxiliares.
     * (criação de objetos como: Scanner e Random)
     */
    private static Scanner sc = new Scanner(System.in);
    private static Random random = new Random();

    /**
     * Porta de entrada do código
     * (Método principal do Jogo)
     */
    public static void main(String[] args) {
        carregarTabuleiro();
        distribuirNavios();

        while (naviosRestantes > 0 && tentativas < TENTATIVAS_MAXIMAS) {
            exibirTabuleiro();
            realizarJogada();
        }

        exibirResultadoFinal();
        sc.close(); // Fecha o Scanner quando o jogo é finalizado.
    }

    /**
     * Preenche o tabuleiro com água
     * Laço externo Itera sobre cada linha
     * laço interno itera sobre cada coluna
     */
    private static void carregarTabuleiro() {
        for (int i = 0; i < TAMANHO_TABULEIRO; i++) {
            for (int j = 0; j < TAMANHO_TABULEIRO; j++) {
                tabuleiro[i][j] = AGUA;
            }
        }
    }

    /**
     * Distribui os navios aleatoriamente no tabuleiro.
     */
    private static void distribuirNavios() {
        int naviosDistribuidos = 0;

        while (naviosDistribuidos < QUANTIDADE_NAVIOS) {
            int linha = random.nextInt(TAMANHO_TABULEIRO);
            int coluna = random.nextInt(TAMANHO_TABULEIRO);

            if (!navios[linha][coluna]) {
                navios[linha][coluna] = true;
                naviosDistribuidos++;
            }
        }
    }

    /**
     * Exibe o tabuleiro atualizado para o jogador.
     */
    private static void exibirTabuleiro() {
        System.out.println("\n  0 1 2 3 4 5 6 7");
        for (int i = 0; i < TAMANHO_TABULEIRO; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < TAMANHO_TABULEIRO; j++) {
                char conteudo = tabuleiro[i][j];
                if (conteudo == NAVIO && naviosRestantes > 0) {
                    conteudo = AGUA; // Esconde os navios do jogador
                }
                System.out.print(conteudo + " ");
            }
            System.out.println();
        }
    }

    /**
     * Realiza a jogada do jogador.
     * Atualiza o estado do jogo.
     */
    private static void realizarJogada() {
        System.out.println("\nRodada #" + (tentativas + 1));
        boolean entradaValida = false;
        int linha = -1;
        int coluna = -1;
    
        // loop solicitando entradas válidas
        while (!entradaValida) {
            System.out.print("Digite as coordenadas para atacar (linha e coluna, separadas por espaço): ");
            String entrada = sc.nextLine();
            String[] tokens = entrada.trim().split("\\s+");
    
            if (tokens.length == 2) {
                // verifica se as coordenadas são números inteiros
                boolean linhaValida = tokens[0].matches("\\d+");
                boolean colunaValida = tokens[1].matches("\\d+");
    
                if (linhaValida && colunaValida) {
                    // converte as coordenadas string para inteiro
                    linha = Integer.parseInt(tokens[0]);
                    coluna = Integer.parseInt(tokens[1]);
    
                    // Valida as coordenadas inseridas pelo jogador
                    if (linha >= 0 && linha < TAMANHO_TABULEIRO && coluna >= 0 && coluna < TAMANHO_TABULEIRO &&
                        tabuleiro[linha][coluna] != TENTATIVA_AGUA && tabuleiro[linha][coluna] != TENTATIVA_ACERTO) {
                        entradaValida = true;
                    } else {
                        System.out.println("Coordenadas inválidas ou já atacadas! Tente novamente.");
                    }
                } else {
                    System.out.println("Coordenada inválida! Por favor, insira números inteiros.");
                }
            } else {
                System.out.println("Coordenada inválida! Por favor, insira duas coordenadas (linha e coluna).");
            }
        }
    
        // Verifica se o ataque acertou um navio
        boolean acertou = navios[linha][coluna];
        tabuleiro[linha][coluna] = acertou ? TENTATIVA_ACERTO : TENTATIVA_AGUA;
    
        // Atualiza o estado do jogo
        if (acertou) {
            naviosRestantes--;
            System.out.println("Você acertou um navio!");
        } else {
            System.out.println("Você errou o ataque.");
        }
    
        tentativas++;
    }

    /**
     * Exibe o resultado final do jogo.
     */
    private static void exibirResultadoFinal() {
        System.out.println("\nTabuleiro final:");
        exibirTabuleiroFinal();

        if (naviosRestantes == 0) {
            System.out.println("Parabéns! Você destruiu todos os navios!");
        } else {
            System.out.println("Fim de jogo! Você não conseguiu destruir todos os navios.");
        }
    }

    /**
     * Exibe o tabuleiro final e revela os demais navios
     */
    private static void exibirTabuleiroFinal() {
        System.out.println("\n  0 1 2 3 4 5 6 7");
        for (int i = 0; i < TAMANHO_TABULEIRO; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < TAMANHO_TABULEIRO; j++) {
                char conteudo = navios[i][j] ? NAVIO : tabuleiro[i][j];
                System.out.print(conteudo + " ");
            }
            System.out.println();
        }
    }
}
