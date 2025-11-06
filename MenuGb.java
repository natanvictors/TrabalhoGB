import java.util.Scanner;

public class MenuGb {
      public static void main(String[] args) {

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
        Scanner scanner = new Scanner(System.in);
        int opcao = scanner. nextInt();
        

        switch (opcao){
            case 1: carregarDados(); break;
            case 2: consultarVaga(); break;
            case 3: registrarEntrada(); break;
            case 4: registrarSaida(); break;
            case 5: mostrarOcupacao(); break;
            case 6: relatorioFinanceiro(); break;
            case 7: salvarDados(); break;
            case 8: System.out.println("Aryel Andrada Bergmann\r\nLucas Furquim Jardim\r\nMagno Augusto Castro Braccini\r\nNatan Victor da Rosa de Oliveira"); break;
            case 0: System.out.println("Saindo..."); break;
            default: System.out.println("Opção inválida!");
            
            scanner.close();
        }
        }
            }
