import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Tudojunto {

    // FINANCEIRO GLOBAL
    static double totalMotosFinanceiro = 0;
    static double totalCarrosFinanceiro = 0;
    static double totalVansFinanceiro = 0;

    // -----------------------
    // CARREGAR DADOS
    // -----------------------
    public static String[][] carregarDados(Scanner scanner, String[][] vagas) {
        System.out.print("\033[H\033[2J");
        System.out.println("=== CARREGAR DADOS ===\n");
        String[][] novaMatriz = new String[vagas.length][vagas[0].length];
        for(int i=0; i<vagas.length; i++){
            Arrays.fill(novaMatriz[i], ".");
        }

        System.out.print("Digite o nome do arquivo (ex.: dados.txt): ");
        String arquivo = scanner.nextLine();
        try {
            FileReader reader = new FileReader(arquivo);
            BufferedReader buffer = new BufferedReader(reader);
            String line;
            String[] separado;
            while((line = buffer.readLine()) != null){
                separado = line.split("=");
                if(separado[0].charAt(0)-'A'>=vagas.length || separado[0].charAt(1)-'1'>=vagas[0].length){
                    System.out.println(separado[0] + "||" + separado[1]);
                    System.out.println("Não foi possível adicionar dados!");
                    buffer.close();
                    return null;
                }
                novaMatriz[separado[0].charAt(0)-'A'][separado[0].charAt(1)-'1'] = separado[1];
            }
            buffer.close();
        } catch (Exception e) {
            System.out.println("Erro ao carregar arquivo!");
            esperarEnter(scanner);
            return null;
        }
        return novaMatriz;
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
        int totalReal = 0;

        for (int i = 0; i < vagas.length; i++) {
            System.out.print((char) ('A' + i) + " ");

            for (int j = 0; j < vagas[0].length; j++) {
                char tipo = vagas[i][j].charAt(0);
                System.out.print(tipo + "  ");

                if (tipo == 'C') qtdCarros++;
                if (tipo == 'M') qtdMotos++;
                if (tipo == 'V') qtdVans++;
                if (tipo != '.') total++;
                totalReal++;
            }
            System.out.println();
        }
        if(total>0){
            System.out.printf("\n%-8s: %2d - %5.1f%% ", "Moto", qtdMotos, (double) qtdMotos/total * 100);
            mostrarGrafico((double) qtdMotos/total*100);
            System.out.printf(" (%d vagas de %d)",qtdMotos, total);
            System.out.printf("\n%-8s: %2d - %5.1f%% ", "Carro", qtdCarros, (double) qtdCarros/total * 100);
            mostrarGrafico((double) qtdCarros/total*100);
            System.out.printf(" (%d vagas de %d)",qtdCarros, total);
            System.out.printf("\n%-8s: %2d - %5.1f%% ", "Van", qtdVans, (double) qtdVans/total * 100);
            mostrarGrafico((double) qtdVans/total*100);
            System.out.printf(" (%d vagas de %d)\n",qtdVans, total);
            System.out.println("-------------------------------------------|");
            System.out.printf("%-8s: %2d - %5.1f%% ", "Ocupadas", total, (double) total/totalReal*100);
            mostrarGrafico((double) total/totalReal*100);
            System.out.printf(" (%d vagas de %d)",total, totalReal);
            System.out.printf("\n%-8s: %2d - %5.1f%% ", "Livres", totalReal-total, (double) (totalReal-total)/totalReal*100);
            mostrarGrafico((double) (totalReal-total)/totalReal*100);
            System.out.printf(" (%d vagas de %d)",totalReal-total, totalReal);
        }else{
            System.out.println("Ainda não há vagas ocupadas!");
        }
        
        



        esperarEnter(scanner);
    }

    public static void mostrarGrafico(double porcentagem){
        System.out.print("|");
        for(int i = 0; i < 20; i++){
            if(Math.floor(porcentagem)>5*i){
                System.out.print("=");
            }else{
                System.out.print(".");
            }
        }
        System.out.print("|");
    }

    // ------------------------------
    // REGISTRAR SAÍDA (FINANCEIRO)
    // ------------------------------
    public static void registrarSaida(String[][] vagas, Scanner scanner, double valorRefPrimeiro, double valorRefResto) {
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
        if(horarioSaida.length()!=5||horarioSaida.charAt(2)!=':'){
            System.out.println("Formato inválido!");
            return;
        }

        int minEntrada = converterParaMinutos(horarioEntrada);
        int minSaida = converterParaMinutos(horarioSaida);

        if (minSaida < minEntrada) {
            System.out.println("Horário inválido!");
            return;
        }


        double valor = calcularValorASerPago(minSaida-minEntrada, valorRefPrimeiro, valorRefResto, tipo);

        switch(tipo){
            case 'C':
                totalCarrosFinanceiro+=valor;
                break;
            case 'V':
                totalVansFinanceiro+=valor;
                break;
            case 'M':
                totalMotosFinanceiro+=valor;
                break;
        }

        vagas[linha][coluna] = ".";

        System.out.printf("\nSaída registrada!\nValor: R$ %.2f", valor);
        esperarEnter(scanner);
    }

    public static double calcularValorASerPago(int minutosNaVaga, double valorRefPrimeiro, double valorRefResto, char tipo){
        switch(tipo){
            case 'M':
                valorRefPrimeiro*=0.7;
                valorRefResto*=0.7;
                break;
            case 'V':
                valorRefPrimeiro*=1.3;
                valorRefResto*=1.3;
                break;
        }

        if(minutosNaVaga<30){
            return valorRefPrimeiro;
        }

        int minutosRestantes = minutosNaVaga - 30;

        return valorRefPrimeiro + Math.ceil(minutosRestantes/30) * valorRefResto;
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

    public static void salvarDados(String[][] vagas, Scanner scanner){
        System.out.println("Insira o nome do arquivo em que deseja salvar (Ex: arquivo.txt)");
        File arquivo = new File(scanner.nextLine());
        String overwrite="";
        boolean append = true;
        if(arquivo.exists()){
            while(!overwrite.equals("S") && !overwrite.equals("N")){
                System.out.println("Deseja sobrescrever o arquivo existente? (s/n)");
                overwrite = scanner.nextLine().toUpperCase();
            }
            if(overwrite.equals("S")){
                append=false;
            }
        }

        String linha;
        try{
            FileWriter writer = new FileWriter(arquivo, append);
            BufferedWriter buffer = new BufferedWriter(writer);
            for(int i=0; i<vagas.length; i++){
                for(int j=0; j<vagas[0].length; j++){
                    if(!vagas[i][j].equals(".")){
                        linha = String.format("%s=%s\n", String.format("%c%d", i+'A', j+1), vagas[i][j]);
                        buffer.write(linha);
                    }
                }
            }
            buffer.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    // --------------------
    // MENU
    // --------------------
    public static void menu(String[][] vagas, double valorRefPrimeiro, double valorRefResto, Scanner scanner) {
        int opcao = 0;

        while (opcao != 9) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Carregar Dados");
            System.out.println("2. Consultar Vaga");
            System.out.println("3. Entrada");
            System.out.println("4. Saída");
            System.out.println("5. Ocupação");
            System.out.println("6. Financeiro");
            System.out.println("7. Salvar Dados");
            System.out.println("8. Integrantes");
            System.out.println("9. Sair");
            System.out.print("Escolha: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    vagas = carregarDados(scanner, vagas);
                    break;

                case 2:
                    consultarVaga(vagas, scanner);
                    break;

                case 3:
                    vagas = registrarEntrada(vagas, scanner);
                    break;

                case 4:
                    registrarSaida(vagas, scanner, valorRefPrimeiro, valorRefResto);
                    break;

                case 5:
                    mostrarOcupacao(vagas, scanner);
                    break;

                case 6:
                    financeiro();
                    esperarEnter(scanner);
                    break;
                case 7:
                    salvarDados(vagas, scanner);
                    break;
                case 8:
                    System.out.println("Integrantes:");
                    System.out.println("Aryel Andrada Bergmann");
                    System.out.println("Lucas Furquim Jardim");
                    System.out.println("Magno Augusto Castro Braccini");
                    System.out.println("Natan Victor da Rosa de Oliveira");
                    esperarEnter(scanner);
                    break;

                case 9:
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
