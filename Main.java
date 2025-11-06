import java.util.Scanner;


public class Main {

    public static void entrada(String[][] vagas){
        Scanner scanner = new Scanner(System.in);

        String tipoVeiculo = "";

        System.out.println("Use (C) para carro, (M) para moto e (V) para van.");
        System.out.print("Informe o tipo de veículo: ");
        tipoVeiculo = scanner.nextLine();

        if (tipoVeiculo.equals("C")|| tipoVeiculo.equals("c")) {
            for (int i = 0; i < vagas.length; i++) {
                
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[][] vagas = new String[5][5];

        // Preenche matriz com "."
        for (int i = 0; i < vagas.length; i++) {
            for (int j = 0; j < vagas[0].length; j++) {
                vagas[i][j] = ".";
            }
        }

        // Imprime números de colunas
        System.out.print("  ");
        for (int col = 1; col <= vagas[0].length; col++) {
            System.out.print(col + " ");
        }
        System.out.println();

        // Imprime letras das linhas
        for (int i = 0; i < vagas.length; i++) {
            char linha = (char) ('A' + i); // A, B, C, D, E...
            System.out.print(linha + " ");
            for (int j = 0; j < vagas[0].length; j++) {
                System.out.print(vagas[i][j] + " ");
            }
            System.out.println();
        }

        scanner.close();
    }
}