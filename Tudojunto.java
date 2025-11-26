import java.util.Scanner;

public class Tudojunto {

    // FINANCEIRO GLOBAL
    static double totalMotosFinanceiro = 0;
    static double totalCarrosFinanceiro = 0;
    static double totalVansFinanceiro = 0;

    // -----------------------
    // CARREGAR DADOS
    // -----------------------
    public static String[][] carregarDados(Scanner scanner, double[] valores) {
        System.out.print("\033[H\033[2J");
        System.out.println("=== CARREGAR DADOS ===\n");

        System.out.print("Digite o nome do arquivo (ex.: dados.txt): ");
        String arquivo = scanner.nextLine();

        try {
            java.io.File file = new java.io.File(arquivo);
            Scanner leitor = new Scanner(file);

            int linhas = leitor.nextInt();
            int colunas = leitor.nextInt();
            leitor.nextLine();

            valores[0] = leitor.nextDouble(); 
            valores[1] = leitor.nextDouble(); 
            leitor.nextLine();

            String[][] vagas = new String[linhas][colunas];

            while (leitor.hasNextLine()) {
                String linha = leitor.nextLine().trim();
                if (linha.length() == 0) continue;

                String[] partes = linha.split(":");

                if (partes.length == 2 && partes[1].equals(".")) {
                    char corredor = partes[0].charAt(0);
                    int linhaIdx = corredor - 'A';
                    int colunaIdx = Integer.parseInt(partes[0].substring(1)) - 1;
                    vagas[linhaIdx][colunaIdx] = ".";
                    continue;
                }

                char corredor = partes[0].charAt(0);
                int linhaIdx = corredor - 'A';
                int colunaIdx = Integer.parseInt(partes[0].substring(1)) - 1;

                char tipo = partes[1].charAt(0);
                String hora = partes[2];
                String min = partes[3];

                vagas[linhaIdx][colunaIdx] = tipo + ":" + hora + ":" + min;
            }

            leitor.close();
            System.out.println("\nDados carregados!");
            esperarEnter(scanner);
            return vagas;

        } catch (Exception e) {
            System.out.println("Erro ao carregar arquivo!");
            esperarEnter(scanner);
            return null;
        }
    }

    // ------------------------------
    // AUXILIAR
    // ------------------------------
    public static int converterParaMinutos(String horario) {
        String[] partes = horario.split(":");
        return Integer.parseInt(partes[0]) * 60 + Integer.parseInt(partes[1]);
    }

    public static boolean estaOcupado(String[][] vagas, int linha, int coluna) {
        return vagas[linha][coluna] != null && !vagas[linha][coluna].equals(".");
    }

    public static void esperarEnter(Scanner scanner) {
        System.out.println("\nAperte ENTER...");
        scanner.nextLine();
    }

    // ------------------------------
    // OCUPAÇÃO
    // ------------------------------
    public static void mostrarOcupacao(String[][] vagas, Scanner scanner) {
        System.out.print("\033[H\033[2J");

        System.out.print("  ");
        for (int col = 1; col <= vagas[0].length; col++) {
            System.out.printf("%02d ", col);
        }
        System.out.println();

        int qtdCarros = 0; 
        int qtdMotos = 0;
        int qtdVans = 0; 
        int total = 0;

        for (int i = 0; i < vagas.length; i++) {
            System.out.print((char) ('A' + i) + " ");

            for (int j = 0; j < vagas[0].length; j++) {
                char tipo = vagas[i][j].charAt(0);
                System.out.print(tipo + "  ");

                if (tipo == 'C') qtdCarros++;
                if (tipo == 'M') qtdMotos++;
                if (tipo == 'V') qtdVans++;
                if (tipo != '.') total++;
            }
            System.out.println();
        }

        int totalVagas = vagas.length * vagas[0].length;

        System.out.println("\nTotal vagas : " + totalVagas);
        System.out.println("Ocupadas    : " + total);
        System.out.println("Livres      : " + (totalVagas - total));

        esperarEnter(scanner);
    }

    // ------------------------------
    // REGISTRAR SAÍDA (FINANCEIRO)
    // ------------------------------
    public static void registrarSaida(String[][] vagas, Scanner scanner) {
        System.out.print("\033[H\033[2J");
        System.out.println("=== REGISTRAR SAÍDA ===\n");

        System.out.print("Digite a vaga (A1, B3...): ");
        String entrada = scanner.nextLine().toUpperCase();

        if (entrada.length() < 2) {
            System.out.println("Entrada inválida!");
            return;
        }

        int linha = entrada.charAt(0) - 'A';
        int coluna;

        try {
            coluna = Integer.parseInt(entrada.substring(1)) - 1;
        } catch (Exception e) {
            System.out.println("Número inválido!");
            return;
        }

        if (linha < 0 || linha >= vagas.length || coluna < 0 || coluna >= vagas[0].length) {
            System.out.println("Vaga inexistente!");
            return;
        }

        if (!estaOcupado(vagas, linha, coluna)) {
            System.out.println("A vaga já está vazia!");
            return;
        }

        String dados = vagas[linha][coluna];
        char tipo = dados.charAt(0);
        String horarioEntrada = dados.substring(2);

        System.out.print("Horário de saída (HH:MM): ");
        String horarioSaida = scanner.nextLine();

        int minEntrada = converterParaMinutos(horarioEntrada);
        int minSaida = converterParaMinutos(horarioSaida);

        if (minSaida < minEntrada) {
            System.out.println("Horário inválido!");
            return;
        }

        double valor = switch (tipo) {
            case 'M' -> 5.0;
            case 'C' -> 10.0;
            case 'V' -> 15.0;
            default -> 0;
        };

        if (tipo == 'M') totalMotosFinanceiro += valor;
        if (tipo == 'C') totalCarrosFinanceiro += valor;
        if (tipo == 'V') totalVansFinanceiro += valor;

        vagas[linha][coluna] = ".";

        System.out.println("\nSaída registrada!\nValor: R$ " + valor);
        esperarEnter(scanner);
    }

    // ------------------------------
    // FINANCEIRO CORRIGIDO
    // ------------------------------
    public static void financeiro() {
        System.out.println("\n----- FINANCEIRO -----\n");

        double total = totalMotosFinanceiro + totalCarrosFinanceiro + totalVansFinanceiro;

        System.out.printf("Motos : R$ %.2f\n", totalMotosFinanceiro);
        System.out.printf("Carros: R$ %.2f\n", totalCarrosFinanceiro);
        System.out.printf("Vans  : R$ %.2f\n", totalVansFinanceiro);
        System.out.println("------------------------");
        System.out.printf("TOTAL : R$ %.2f\n", total);
        System.out.println("------------------------");

    }

    // ------------------------------
    // CONSULTAR VAGA
    // ------------------------------
    public static void consultarVaga(String[][] vagas, Scanner scanner) {
        System.out.print("\033[H\033[2J");
        System.out.println("CONSULTAR VAGA");

        System.out.println("Informe A1, corredor (A) ou ENTER para primeira vaga livre:");
        String entrada = scanner.nextLine().trim().toUpperCase();

        if (entrada.length() == 0) {
            for (int i = 0; i < vagas.length; i++) {
                for (int j = 0; j < vagas[0].length; j++) {
                    if (vagas[i][j].equals(".")) {
                        System.out.println("Primeira vaga livre: " + (char) ('A' + i) + (j + 1));
                        esperarEnter(scanner);
                        return;
                    }
                }
            }
            System.out.println("Estacionamento cheio!");
            esperarEnter(scanner);
            return;
        }

        if (entrada.length() == 1 && entrada.charAt(0) >= 'A') {
            int linha = entrada.charAt(0) - 'A';

            if (linha < 0 || linha >= vagas.length) {
                System.out.println("Corredor não existe!");
                esperarEnter(scanner);
                return;
            }

            System.out.println("\nCorredor " + entrada.charAt(0) + ":");
            for (int j = 0; j < vagas[linha].length; j++) {
                System.out.println("Vaga " + (j + 1) + ": " + vagas[linha][j]);
            }
            esperarEnter(scanner);
            return;
        }

        try {
            int linha = entrada.charAt(0) - 'A';
            int coluna = Integer.parseInt(entrada.substring(1)) - 1;
            String status;
            if (linha < 0 || coluna < 0 || linha >= vagas.length || coluna >= vagas[0].length) {
                System.out.println("Vaga não existe!");
            } else {
                if(vagas[linha][coluna].equals(".")){
                    status = "LIVRE";
                }else{
                    status = "OCUPADA";
                }
                System.out.println("Status da vaga " + entrada + ": " + status);
            }

        } catch (Exception e) {
            System.out.println("Formato inválido!");
        }

        esperarEnter(scanner);
    }

    // ------------------------------
    // REGISTRAR ENTRADA
    // ------------------------------
    public static String[][] registrarEntrada(String[][] vagas, Scanner scanner) {
        System.out.print("\033[H\033[2J");

        System.out.println("Tipo do veículo (C / M / V):");
        char veiculo = Character.toUpperCase(scanner.next().charAt(0));

        System.out.println("Hora:");
        int hora = scanner.nextInt();

        System.out.println("Minuto:");
        int minuto = scanner.nextInt();
        scanner.nextLine();

        if (veiculo != 'C' && veiculo != 'M' && veiculo != 'V' ||
                hora < 0 || hora > 23 || minuto < 0 || minuto > 59) {
            System.out.println("Dados inválidos!");
            esperarEnter(scanner);
            return vagas;
        }

        System.out.println("Escolher vaga (A1, B3...) ou ENTER p/ automática:");
        String escolha = scanner.nextLine().trim().toUpperCase();

        if (escolha.length() == 0) {
            for (int i = 0; i < vagas.length; i++) {
                for (int j = 0; j < vagas[0].length; j++) {
                    if (vagas[i][j].equals(".")) {
                        vagas[i][j] = String.format("%c:%02d:%02d", veiculo, hora, minuto);
                        System.out.println("Vaga atribuída automaticamente!");
                        esperarEnter(scanner);
                        return vagas;
                    }
                }
            }
            System.out.println("Estacionamento cheio!");
            esperarEnter(scanner);
            return vagas;
        }

        try {
            int linha = escolha.charAt(0) - 'A';
            int coluna = Integer.parseInt(escolha.substring(1)) - 1;

            if (vagas[linha][coluna].equals(".")) {
                vagas[linha][coluna] = String.format("%c:%02d:%02d", veiculo, hora, minuto);
                System.out.println("Entrada registrada!");
            } else {
                System.out.println("Vaga já ocupada!");
            }

        } catch (Exception e) {
            System.out.println("Formato inválido!");
        }

        esperarEnter(scanner);
        return vagas;
    }

    // --------------------
    // MENU
    // --------------------
    public static void menu(String[][] vagas, double valorRefPrimeiro, double valorRefResto, Scanner scanner) {
        int opcao = 10;

        while (opcao != 0) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Carregar Dados");
            System.out.println("2. Consultar Vaga");
            System.out.println("3. Entrada");
            System.out.println("4. Saída");
            System.out.println("5. Ocupação");
            System.out.println("6. Financeiro");
            System.out.println("7. Integrantes");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    double[] valores = new double[2];
                    String[][] novo = carregarDados(scanner, valores);
                    if (novo != null) vagas = novo;
                    break;

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

                case 6:
                    financeiro();
                    esperarEnter(scanner);
                    break;

                case 7:
                    System.out.println("Integrantes:");
                    System.out.println("Aryel Andrada Bergmann");
                    System.out.println("Lucas Furquim Jardim");
                    System.out.println("Magno Augusto Castro Braccini");
                    System.out.println("Natan Victor da Rosa de Oliveira");
                    esperarEnter(scanner);
                    break;

                case 0:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    // ------------------------------
    // MAIN
    // ------------------------------
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Sistema de Estacionamento");

        System.out.print("Valor 1º período: ");
        double valorRefPrimeiro = scanner.nextDouble();

        System.out.print("Valor demais períodos: ");
        double valorRefResto = scanner.nextDouble();

        System.out.print("Nº de corredores (5-15): ");
        int linhas = scanner.nextInt();

        System.out.print("Nº de vagas por corredor (5-20): ");
        int colunas = scanner.nextInt();

        String[][] vagas = new String[linhas][colunas];

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) vagas[i][j] = ".";
        }

        scanner.nextLine();
        menu(vagas, valorRefPrimeiro, valorRefResto, scanner);

        scanner.close();
    }
}
