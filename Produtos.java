package ProjetoCRUD;

// Bruno Ferreira, Eveliny Muniz, Camila Melo, Felipe Menezes, Filipe Boddenberg :)

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Produtos {

    static Scanner sc = new Scanner(System.in);
    static Path path = Paths.get(".\\estoque.txt");
    static Path log = Paths.get(".\\logCompras.txt");

    public static void main(String[] args) throws IOException {

        boolean continuar = true;
        String opcaoEntrada;

        System.out.println("\n.:: Gerenciamento de produtos ::.");

        while(continuar) {
            System.out.println("\nEscolha um das opções abaixo: ");
            System.out.println("0 - Listar produtos");
            System.out.println("1 - Cadastrar produto");
            System.out.println("2 - Editar produto");
            System.out.println("3 - Excluir produto");
            System.out.println("4 - Pesquisar produto");
            System.out.println("5 - Fazer compras");
            System.out.println("6 - Sair");

            opcaoEntrada = sc.nextLine();

            switch (opcaoEntrada) {
                case "0" -> listarProdutos();
                case "1" -> criarProduto();
                case "2" -> editarProduto();
                case "3" -> excluirProduto();
                case "4" -> pesquisarProduto();
                case "5" -> fazerCompras();
                case "6" -> continuar = false;
                default -> System.out.println("Ops, opção inválida!");
            }
        }
<<<<<<< HEAD

=======
>>>>>>> 3358b3e1a2ca8125452b5cfc3cc6180b2fc2da70
    }
    

    public static List<String> listarProdutos() throws IOException {
        List<String> listaProdutos = lerArquivoProdutos();

        System.out.println("\n.:: Lista de produtos cadastrados ::.\n");

        if(listaProdutos.size() > 0) {
            imprimirProdutos(listaProdutos);
        } else {
            System.out.println("Não existem produtos cadastrados.");
        }
        return listaProdutos;
    }

    public static void imprimirProdutos(List<String> listaProdutos) {
        String nome, quantidadeProduto;
        Double precoProduto;
        Integer id = 1;

        for(String string: listaProdutos) {
            nome = string.split("\\|")[0];
            quantidadeProduto = string.split("\\|")[1];
            precoProduto = Double.valueOf(string.split("\\|")[2]);

            System.out.println("- id: " + id + " -");
            System.out.println("Produto: " + nome);
            System.out.println("Quantidade: " + quantidadeProduto);
            System.out.printf("Preço: R$ %.2f", precoProduto);
            System.out.println("\n");
            id++;
        }
    }

    public static void criarProduto() throws IOException {
        System.out.println("\n.:: Cadastro de produtos ::.\n");

        String produtoFormatado = pegarInformacoesDoProduto();
        escreverNoArquivo(path, produtoFormatado, StandardOpenOption.APPEND);
    }

    public static String pegarInformacoesDoProduto() {
        System.out.println("Digite o nome do produto: ");
        String nomeProduto = sc.nextLine();

        System.out.println("Digite a quantidade do produto: ");
        Integer quantidadeProduto = sc.nextInt();

        System.out.println("Digite o preço do produto: ");
        Double precoProduto = sc.nextDouble();
        sc.nextLine();

        String produtoFormatado = nomeProduto.toUpperCase()+"|"+quantidadeProduto+"|"+precoProduto+"\n";
        return produtoFormatado;
    }

    public static void escreverNoArquivo(Path arquivo, String produto, StandardOpenOption option) throws IOException {
        if(!Files.exists(arquivo)) {
            Files.createFile(arquivo);
        }
        if(option != null) {
            Files.writeString(arquivo, produto, option);
        } else {
            Files.writeString(arquivo, produto);
        }
    }

    public static void editarProduto() throws IOException {
        List<String> listaProdutos = listarProdutos();

        System.out.println("\n.:: Edição de produtos ::.\n");
        System.out.println("Informe o id do produto que deseja alterar: ");

        int id = sc.nextInt();
        sc.nextLine();

        String produtoFormatado = pegarInformacoesDoProduto();

        String listaAlterada = "";

        for (int i = 0; i < listaProdutos.size(); i++) {
            if((id-1) == i) {
                listaAlterada += produtoFormatado;
            } else listaAlterada += listaProdutos.get(i) + "\n";
        }

        escreverNoArquivo(path, listaAlterada, null);
    }

    public static void excluirProduto() throws IOException {
        List<String> listaProdutos = listarProdutos();

        System.out.println("\n.:: Exclusão de produtos ::.\n");
        System.out.println("Informe o id do produto que deseja excluir: ");

        int id = sc.nextInt();
        sc.nextLine();

        String listaAlterada = "";

        for (int i = 0; i < listaProdutos.size(); i++) {
            if((id-1) != i) {
                listaAlterada += listaProdutos.get(i) + "\n";
            }
        }

        escreverNoArquivo(path, listaAlterada, null);
    }

    public static void pesquisarProduto() throws IOException {
        System.out.println("\n.:: Pesquisa de produtos ::.\n");
        System.out.println("Informe o termo que deseja pesquisar: ");

        String nomeFiltrado = sc.nextLine().trim().toUpperCase();

        List<String> produto = lerArquivoProdutos();
        List<String> produtosFiltrados = new ArrayList<>();

        for (int i = 0; i < produto.size(); i++) {
            String nomeProduto = produto.get(i).split("\\|")[0];
            if((nomeProduto.contains(nomeFiltrado)) || nomeProduto.equals(nomeFiltrado)) {
                produtosFiltrados.add(produto.get(i));
            }
        }

        if (produtosFiltrados.size() > 0) {
            imprimirProdutos(produtosFiltrados);
        }
        else {
            System.out.println("\nNão existem produtos com o termo indicado.");
        }

    }

    public static List<String> lerArquivoProdutos() throws IOException {
        if(!Files.exists(path)){
            Files.createFile(path);
        }
        List<String> listaProdutos = Files.readAllLines(path);

        return listaProdutos;
    }

    public static void fazerCompras() throws IOException {
        List<String> listaProdutos = listarProdutos();

        String nomeProduto;
        Integer quantidadeProduto;
        Double valorProduto, somaParcial;
        Double somaTotal = 0d;

        List<String> produtosComprados = new ArrayList<>();
        String logCompras = "";

        System.out.println("\n.:: Vamos às compras ::.\n");

        boolean continuarComprando = false;
        do {
            System.out.println("Informe o id do produto que deseja comprar: ");
            Integer id = sc.nextInt();

            System.out.println("Informe a quantidade do produto que deseja: ");
            Integer quantidade = sc.nextInt();
            sc.nextLine();

            for (int i = 0; i < listaProdutos.size(); i++) {
                if((id - 1) == i) {
                    nomeProduto = listaProdutos.get(i).split("\\|")[0];
                    quantidadeProduto = Integer.valueOf(listaProdutos.get(i).split("\\|")[1]);
                    valorProduto = Double.valueOf(listaProdutos.get(i).split("\\|")[2]);

                    if ((quantidade <= quantidadeProduto) && quantidade != 0) {
                        somaParcial = quantidade * valorProduto;
                        somaTotal += somaParcial;

                        String produtoFormatado = nomeProduto + "|" + quantidade + "|" + valorProduto + "|" + somaParcial + "\n";

                        produtosComprados.add(produtoFormatado);
                        logCompras += produtoFormatado;

                        atualizarEstoque(listaProdutos, id, nomeProduto, (quantidadeProduto - quantidade), valorProduto);
                    } else if (quantidade==0) {
                        System.out.println("Quantidade deve ser superior a 0.");
                    } else {
                        System.out.println("Ops... não há quantidade o suficiente em estoque.");
                    }
                }
            }

            System.out.println("Deseja comprar outro(s) produtos?");
            System.out.println("(1) para sim.");
            System.out.println("(2) para não.");
            String opcao = sc.nextLine();

            continuarComprando = (opcao.equals("1"))? true : false;
        } while (continuarComprando);

        logCompras += "Valor total desta compra: ".toUpperCase() + somaTotal + "\n";

        imprimirCompra(produtosComprados, somaTotal);
        escreverNoArquivo(log, logCompras, StandardOpenOption.APPEND);
    }

    public static void atualizarEstoque(List<String> listaProdutos, Integer id, String nome, Integer quantidade, Double valor) throws IOException {

        String produtoFormatado = nome + "|" + quantidade + "|" + valor + "\n";

        String listaAlterada = "";

        for (int i = 0; i < listaProdutos.size(); i++) {
            if((id-1) == i) {
                listaAlterada += produtoFormatado;
            } else listaAlterada += listaProdutos.get(i) + "\n";
        }

        escreverNoArquivo(path, listaAlterada, null);
    }

    public static void imprimirCompra(List<String> listaProdutos, Double valorTotal) {
        String nome, quantidadeProduto;
        Double precoProduto, valorParcial;
        Integer id = 1;

        for(String string: listaProdutos) {
            nome = string.split("\\|")[0];
            quantidadeProduto = string.split("\\|")[1];
            precoProduto = Double.valueOf(string.split("\\|")[2]);
            valorParcial = Double.valueOf(string.split("\\|")[3]);

            System.out.println("- Item: " + id + " -");
            System.out.println("Produto: " + nome);
            System.out.println("Quantidade: " + quantidadeProduto);
            System.out.printf("Preço: R$ %.2f\n", precoProduto);
            System.out.printf("Valor parcial: R$ %.2f", valorParcial);
            System.out.println("\n");
            id++;
        }
        System.out.printf("Valor total da compra: R$ %.2f\n", valorTotal);
    }
}