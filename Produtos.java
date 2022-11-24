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
    static List<String> listaProdutos = new ArrayList<>();
    static Path path = Paths.get(".\\produtos.txt");

    public static void main(String[] args) throws IOException{
        Scanner sc = new Scanner(System.in);
        boolean continuar=true;
        String opcaoEntrada;

        while(continuar){
            System.out.println("\nEscolha um das opções abaixo");
            System.out.println("0-Listar produtos");
            System.out.println("1-Cadastrar produto");
            System.out.println("2-Editar produto");
            System.out.println("3-Excluir produto");
            System.out.println("4-Filtrar produto");
            System.out.println("5-Sair\n");
            opcaoEntrada = sc.nextLine();

            switch (opcaoEntrada){
                case "0"-> listarProdutos();
                case "1"-> criarProduto(sc);
                case "2"-> editarProduto(sc);
                case "3"-> excluirProduto(sc);
                case "4"-> pesquisarProduto(sc);
                case "5"-> continuar=false;
                default-> System.out.println("Opção inválida");
            }
        }





    }

    public static void criarProduto(Scanner sc) throws IOException {
        String produtoFormatado = pegarInformacoesDoProduto(sc);
        
        if(!Files.exists(path)){
            Files.createFile(path);
        }
        Files.writeString(path, produtoFormatado, StandardOpenOption.APPEND);
        // ,StandardOpenOption.CREATE 
        //  testar caso nao haja arquivo vazia
    }

    public static String pegarInformacoesDoProduto(Scanner sc) {
        System.out.println("Digite o nome do produto");
        String nomeProduto = sc.nextLine();
        System.out.println("Digite a quantidade do produto");
        Integer quantidadeProduto = sc.nextInt();
        System.out.println("Digite o preço do produto");
        Float precoProduto = sc.nextFloat();
        sc.nextLine();
        
        String produtoFormatado = nomeProduto.toUpperCase()+"|"+quantidadeProduto+"|"+precoProduto+"\n";
        return produtoFormatado;
    }


    public static void listarProdutos() throws IOException {
        List<String> listaProdutos = lerArquivoProdutos();
        String nome, quantidadeProduto, precoProduto;
        Integer iD=1;

        for(String string: listaProdutos){
            nome = string.split("\\|")[0];
            quantidadeProduto = string.split("\\|")[1];
            precoProduto = string.split("\\|")[2];
            System.out.println("id: " + iD);
            System.out.println("nome: " + nome);
            System.out.println("quantidade de produto: " + quantidadeProduto);
            System.out.println("preço do produto: " + precoProduto);
            iD++;
        }
    }

    public static void editarProduto(Scanner sc) throws IOException {
        listarProdutos();
        System.out.println("Digite o ID do produto que deseja alterar");
        int iD = sc.nextInt();
        sc.nextLine();
        String produtoFormatado = pegarInformacoesDoProduto(sc);
        List<String> listaProdutos = lerArquivoProdutos();
        
        String listaAlterada= "";
        for (int i = 0; i < listaProdutos.size(); i++) {
            if((iD-1)==i){
                listaAlterada+= produtoFormatado;
             } else listaAlterada+=listaProdutos.get(i)+"\n";
        }
        Files.writeString(path, listaAlterada);
    }

    public static void excluirProduto(Scanner sc) throws IOException {
        listarProdutos();
        System.out.println("Digite o ID do produto que deseja excluir");
        int iD = sc.nextInt();
        sc.nextLine();
        List<String> listaProdutos = lerArquivoProdutos();
        
        String listaAlterada= "";
        for (int i = 0; i < listaProdutos.size(); i++) {
            if((iD-1)!=i){
                listaAlterada+=listaProdutos.get(i)+"\n";
             }
        }
        Files.writeString(path, listaAlterada);
    }
    



    public static List<String> lerArquivoProdutos() throws IOException {
        List<String> listaProdutos = Files.readAllLines(path);
        
        return listaProdutos;
    }

    public static void pesquisarProduto( Scanner sc) throws IOException {
        System.out.println("Digite o nome do produto a ser filtrado");
        String nomeFiltrado = sc.nextLine().toUpperCase();

        List<String> produto = lerArquivoProdutos();
        List<String> produtosFiltrados = new ArrayList<>();

        for (int i = 0; i < produto.size(); i++) {
            String nomeProduto = produto.get(i).split("\\|")[0];
            if((nomeProduto.contains(nomeFiltrado)) || nomeProduto.equals(nomeFiltrado)){
                produtosFiltrados.add(produto.get(i));
            }
        }
        System.out.println(produtosFiltrados);
    }
    
}

