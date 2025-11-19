import java.util.Scanner;

public class Tudojunto {
    public static void mostrarOcupacao(String[][] vagas, Scanner scanner) {
        System.out.print("\033\143");

        // Imprime números de colunas
        System.out.print("  ");
        for (int col = 1; col <= vagas[0].length; col++) {
            System.out.printf("%02d ", col);
        }
        System.out.println();

        // Imprime letras das linhas
        for (int i = 0; i < vagas.length; i++) {
            char linha = (char) ('A' + i); // A, B, C, D, E...
            System.out.print(linha + " ");
            for (int j = 0; j < vagas[0].length; j++) {
                System.out.print(vagas[i][j].charAt(0) + "  ");
            }
            System.out.println();
        }

        System.out.println("Aperte enter para voltar");
        scanner.nextLine();
        scanner.nextLine();
    }

    public static void menu(String[][] vagas, double valorRefPrimeiro, double valorRefResto, Scanner scanner) {
        int opcao = 10;

        while(opcao!=0){
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
        opcao = scanner.nextInt();
        

        switch (opcao){
           // case 1: carregarDados(); break;
           // case 2: consultarVaga(); break;
            case 3: 
                vagas = registrarEntrada(vagas, scanner);
                break;
           // case 4: registrarSaida(); break;
            case 5: 
                mostrarOcupacao(vagas, scanner);
                break;
           // case 6: relatorioFinanceiro(); break;
           // case 7: salvarDados(); break;
            case 8: 
                System.out.println("Aryel Andrada Bergmann\r\nLucas Furquim Jardim\r\nMagno Augusto Castro Braccini\r\nNatan Victor da Rosa de Oliveira");
                break;
            case 0: 
                System.out.println("Saindo..."); 
                break;
            default: System.out.println("Opção inválida!");
        }
        }
        
    }
    
    public static String[][] registrarEntrada(String[][] vagas, Scanner scanner){
        System.out.print("\033\143");

        char veiculo;
        int hora, minuto;

        System.out.println("Insira o caracter correspondente ao tipo do seu veículo (C = carro, M = moto, V = van)");
        veiculo = scanner.next().charAt(0);

        System.out.println("Agora insira a hora de entrada do veículo");
        hora = scanner.nextInt();

        System.out.println("Por fim, insira o minuto de entrada");
        minuto = scanner.nextInt();

        if(veiculo!='C'&&veiculo!='V'&&veiculo!='M'||hora>24||hora<0||minuto>60||minuto<0){
            System.out.println("Dados inválidos! Enter para voltar ao menu principal: "+ veiculo +" "+hora+" "+minuto);
            scanner.nextLine();
            scanner.nextLine();
            return vagas;
        }

        String infos;
        System.out.println("Adicione alguma informação caso deseje (Vaga desejada, corredor desejado), caso não queira, apenas tecle Enter.");
        scanner.nextLine();
        infos = scanner.nextLine();
        char corredor;
        char vaga;
        

        switch (infos.length()) {
            case 1:
                corredor = infos.charAt(0);
                boolean mudou = false;
                int i = 0;
                while(mudou == false){
                    if(vagas[(int) corredor - 'A'][i]=="."){
                        vagas[(int) corredor - 'A'][i]=String.format("%c:%02d:%02d", veiculo, hora, minuto);
                        mudou = true;
                    }
                    i++;
                }
                break;
            case 2:
                corredor = infos.charAt(0);
                vaga = infos.charAt(1);
                if(vagas[corredor-'A'][vaga-'1']=="."){
                   vagas[corredor-'A'][vaga-'1']=String.format("%c:%02d:%02d", veiculo, hora, minuto);
                }
                else{
                    System.out.println("Essa vaga já está ocupada! Enter para voltar ao menu");
                    scanner.nextLine();
                    return vagas;
                }

            default:
                break;
        }


        scanner.nextLine();
        return vagas;
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

        for (int i = 0; i < vagas.length; i++) {
            for (int j = 0; j < vagas[0].length; j++) {
                vagas[i][j] = ".";
            }
        }

        menu(vagas, valorRefPrimeiro, valorRefResto, scanner);

        scanner.close();
    }


}
