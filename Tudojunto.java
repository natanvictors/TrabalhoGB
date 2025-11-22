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
        int qtdCarros = 0, qtdMotos = 0, qtdVans = 0, total = 0;
        
        // Imprime letras das linhas
        for (int i = 0; i < vagas.length; i++) {
            char linha = (char) ('A' + i); 
            System.out.print(linha + " ");
            for (int j = 0; j < vagas[0].length; j++) {
                boolean ocupado = true;
                char tipo = vagas[i][j].charAt(0);
                System.out.print(tipo + "  ");
                switch(tipo){
                    case 'C':
                        qtdCarros++;
                        break;
                    case 'V':
                        qtdVans++;
                        break;
                    case 'M':
                        qtdMotos++;
                        break;
                    default:
                        ocupado = false;
                }
                if(ocupado){
                    total++;
                }
            }
            System.out.println();
        }
        System.out.println();
        if(total>0){
            System.out.printf("%-8s: %2d - %.1f%%\n", "Moto", qtdMotos, (double) qtdMotos/total*100);
            System.out.printf("%-8s: %2d - %.1f%%\n", "Carro", qtdCarros, (double) qtdCarros/total*100);
            System.out.printf("%-8s: %2d - %.1f%%\n", "Van", qtdVans, (double) qtdVans/total*100);
        }else{
            System.out.printf("%-8s: %2d - %.1f%%\n", "Moto", qtdMotos, 0.0);
            System.out.printf("%-8s: %2d - %.1f%%\n", "Carro", qtdCarros, 0.0);
            System.out.printf("%-8s: %2d - %.1f%%\n", "Van", qtdVans, 0.0);
        }
        
        System.out.println("Aperte enter para voltar");
        scanner.nextLine();
        scanner.nextLine();
    }

    public static boolean estaOcupado(String[][] vagas, int corredor, int vaga) {

        if(vagas[corredor][vaga].equals(".")){
            return true;
        }

        return false;
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
           case 2: 
                 consultarVaga(vagas, scanner); 
                 break;
            case 3: 
                vagas = registrarEntrada(vagas, scanner);
                break;
            case 4: 
                registrarSaida(vagas, scanner);
                break;
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
        veiculo = Character.toUpperCase(veiculo);

        System.out.println("Agora insira a hora de entrada do veículo");
        hora = scanner.nextInt();

        System.out.println("Por fim, insira o minuto de entrada");
        minuto = scanner.nextInt();

        if(veiculo!='C' && veiculo!='V' && veiculo!='M' || hora>24 || hora<0 || minuto>60 || minuto<0){
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
        int vaga;
        int total = 0;

        switch (infos.length()) {
            case 0:
                int i = 0;
                boolean mudou = false;
                int j = 0;
                while(mudou == false){
                    if (vagas[i][j].equals(".")) {
                        vagas[i][j] = String.format("%c:%02d:%02d", veiculo, hora, minuto);
                        mudou = true;
                    }else{
                        total++;
                    }
                    if(total == vagas[i].length*vagas[j].length){
                        System.out.println("O estacionamento está lotado!");
                        break;
                    }
                    if(j==vagas[i].length-1 && i < vagas.length-1){
                        j=0;
                        i++;
                    }else{
                        j++;
                    }
                }
                break;
            case 1:
                corredor = infos.charAt(0);
                mudou = false;
                i = 0;
                while(mudou == false){
                    if (vagas[(int) corredor - 'A'][i].equals(".")) {
                        vagas[(int) corredor - 'A'][i] = String.format("%c:%02d:%02d", veiculo, hora, minuto);
                        mudou = true;
                    }
                    i++;
                }                
                break;
            default:
                corredor = infos.charAt(0);
                vaga = Integer.parseInt(infos.substring(1));
                if (vagas[corredor - 'A'][vaga - 1].equals(".")) {
                    vagas[corredor - 'A'][vaga - 1] = String.format("%c:%02d:%02d", veiculo, hora, minuto);
                } else{
                    System.out.println("Essa vaga já está ocupada! Enter para voltar ao menu");
                    scanner.nextLine();
                    return vagas;
                }
                break;
        }


        scanner.nextLine();
        return vagas;
    }

    public static void consultarVaga(String[][] vagas, Scanner scanner) {
    String entrada = "";
    System.out.print("\033\143");

    System.out.println("CONSULTAR VAGA");
    System.out.println("Digite a vaga no formato (Ex: A1, B3, D10): ");
    scanner.nextLine();  
    entrada = scanner.nextLine().toUpperCase();

    if (entrada.length() < 2 || entrada.length() > 3) {
        System.out.println("Entrada inválida! Tecle Enter para voltar.");
        scanner.nextLine();
        return;
    }

    char corredorChar = entrada.charAt(0);

    if (corredorChar < 'A' || corredorChar >= 'A' + vagas.length) {
        System.out.println("Corredor inexistente! Tecle Enter para voltar.");
        scanner.nextLine();
        return;
    }

    String numeroStr = entrada.substring(1);

    for (int i = 0; i < numeroStr.length(); i++) {
        if (!Character.isDigit(numeroStr.charAt(i))) {
            System.out.println("Número da vaga inválido! Tecle Enter para voltar.");
            scanner.nextLine();
            return;
        }
    }

    int numeroVaga = Integer.parseInt(numeroStr);

    if (numeroVaga < 1 || numeroVaga > vagas[0].length) {
        System.out.println("Essa vaga não existe! Tecle Enter para voltar.");
        scanner.nextLine();
        return;
    }

    int linha = corredorChar - 'A';
    int coluna = numeroVaga - 1;

    System.out.println("\n--- RESULTADO ---");
    if (vagas[linha][coluna].equals(".")) {
        System.out.println("A vaga " + entrada + " está LIVRE.");
    } else {
        System.out.println("A vaga " + entrada + " está OCUPADA.");
        System.out.println("Informações: " + vagas[linha][coluna]);
    }

    System.out.println("\nAperte Enter para voltar.");
    scanner.nextLine();
}

    public static void registrarSaida(String[][] vagas, Scanner scanner) {
        System.out.print("\033\143");
        System.out.println("Digite as coordenadas da vaga que deseja desocupar");
        String coords = scanner.nextLine();
        int corredor = coords.charAt(0) - 'A';
        int vaga = Integer.parseInt(coords.substring(1));

        if(corredor<vagas.length && corredor >= 0 && vaga < vagas[0].length && vaga>0){
            if(estaOcupado(vagas, corredor, vaga)){
                int horaEntradaEmMin = Integer.parseInt(vagas[corredor][vaga].substring(2, 4))*60;
                int minutoEntrada = Integer.parseInt(vagas[corredor][vaga].substring(5));
                int horaSaidaEmMin = 0, minutoSaida = 0;

                while(horaSaidaEmMin+minutoSaida > horaEntradaEmMin+minutoEntrada){
                    System.out.println("Insira o horário de saída do veículo");
                    horaSaidaEmMin = scanner.nextInt()*60;
                    System.out.println("Agora, insira o minuto de saída do veículo");
                    minutoSaida = scanner.nextInt();

                    if(horaSaidaEmMin+minutoSaida > horaEntradaEmMin+minutoEntrada){
                        System.out.println("Horário de saída inválido!");
                    }
                }

                
            }else{
                System.out.println("A vaga não está ocupada! Enter para sair");

            }
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
        System.out.printf("\n\nInforme quantos corredores o estacionamento possui (Entre 5 e 15): ");
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