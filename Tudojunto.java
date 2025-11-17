import java.util.Scanner;

public class Tudojunto {
    public static void mostrarOcupacao(String[][] vagas) {

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
    }

    public static void menu(String[][] vagas, double valorRefPrimeiro, double valorRefResto, Scanner scanner) {

        System.out.println("\n--- MENU ---");
        System.out.println("1. Carregar Dados");
        System.out.println("2. Consultar Vaga");
        System.out.println("3. Entrada");
        System.out.println("4. Saída");
        System.out.println("5. Ocupação");
        System.out.println("6. Financeiro");
        System.out.println("7. Salvar Dados");
        System.out.println("8. Integrantes");
        System.out.println("0. Sair");
        System.out.print("Escolha: ");
        int opcao = scanner.nextInt();
        

        switch (opcao){
           // case 1: carregarDados(); break;
           // case 2: consultarVaga(); break;
           // case 3: registrarEntrada(); break;
           // case 4: registrarSaida(); break;
            case 5: mostrarOcupacao(vagas);
                break;
           // case 6: relatorioFinanceiro(); break;
           // case 7: salvarDados(); break;
           // case 8: System.out.println("Aryel Andrada Bergmann\r\nLucas Furquim Jardim\r\nMagno Augusto Castro Braccini\r\nNatan Victor da Rosa de Oliveira"); break;
            //case 0: System.out.println("Saindo..."); break;
            default: System.out.println("Opção inválida!");
        }
        }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Bem-vindo ao Sistema para Gerenciamento de Estacionamento!");
        System.out.printf("\n\nPrimeiramente, informe o valor de referência para os primeiros 30 minutos: ");
        double valorRefPrimeiro = scanner.nextDouble();
        
        System.out.printf("Agora, informe o valor para os 30 minutos subsequentes: ");
        double valorRefResto = scanner.nextDouble();

        System.out.println("Muito obrigado pelas informações! agora sobre o espaço do estacionamento:");
        System.out.printf("\n\n Informe quantos corredores o estacionamento possui (Entre 5 e 15): ");
        int linhas = 0;
        while(linhas<5 || linhas>15){
           linhas = scanner.nextInt();
           if(linhas<5 || linhas>15){
            System.out.println("Número inválido! Tente novamente: ");
           }
        }
        
        System.out.printf("Informe quantas vagas existem para cada corredor (Entre 5 e 20): ");
        int colunas = 0;
        while(colunas<5 || colunas>20){
           colunas = scanner.nextInt();
           if(colunas<5 || colunas>20){
           System.out.println("Número inválido! Tente novamente: ");
           }
        }

        String[][] vagas = new String[linhas][colunas];

        menu(vagas, valorRefPrimeiro, valorRefResto, scanner);

        scanner.close();
    }


}
