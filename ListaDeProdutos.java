
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ListaDeProdutos {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Object> produtos = new ArrayList<>();

        // criaProduto(produtos, sc);
        // System.out.println(produtos);
        boolean continuar=true;
        String opcaoEntrada;

        while(continuar){
            System.out.println("Escolha um das opções abaixo");
            System.out.println("0-Listar produtos");
            System.out.println("1-Cadastrar produto");
            System.out.println("2-Editar produto");
            System.out.println("3-Excluir produto");
            System.out.println("4-Filtrar produto");
            System.out.println("5-Sair\n");
            opcaoEntrada = sc.nextLine();
            switch (opcaoEntrada){
                case "0"-> System.out.println(produtos + "\n");
                case "1"-> criaProduto(produtos,sc);
                case "2"-> editaProduto(produtos,sc);
                case "3"-> excluiProduto(produtos,sc);
                case "4"-> pesquisaProduto(produtos,sc);
                case "5"-> continuar=false;
                default-> System.out.println("Opção inválida");
            }
        }


        
    }

    public static void criaProduto(List<Object> produtos, Scanner sc) {
        System.out.println("Digite o nome do produto");
        String nomeProduto = sc.nextLine();
        System.out.println("Digite a quantidade do produto");
        Integer quantidadeProduto = sc.nextInt();
        System.out.println("Digite o preço do produto");
        Float preçoProduto = sc.nextFloat();
        sc.nextLine();

        int ID = produtos.size();
        // Object -> ID,nome,quantidade e preço
        List<Object> produto = new ArrayList<>();
        Collections.addAll(produto, ID, nomeProduto,quantidadeProduto,preçoProduto);
        produtos.add(produto);
    }

    public static void editaProduto(List<Object> produtos, Scanner sc) {
        List<Object> produto = new ArrayList<>();
        System.out.println(produtos + "\n\n");
        System.out.println("Digite o ID do produto que deseja alterar");
        int ID = sc.nextInt();
        sc.nextLine();

        System.out.println("Digite o novo nome do produto");
        String nomeProduto = sc.nextLine();
        System.out.println("Digite a nova quantidade do produto");
        Integer quantidadeProduto = sc.nextInt();
        System.out.println("Digite o novo preço do produto");
        Float preçoProduto = sc.nextFloat();
        sc.nextLine();

        Collections.addAll(produto, ID, nomeProduto,quantidadeProduto,preçoProduto);
        produtos.set(ID, produto);
    }

    public static void excluiProduto(List<Object> produtos, Scanner sc) {
        System.out.println(produtos + "\n\n");
        System.out.println("Digite o ID do produto que deseja excluir");
        int ID = sc.nextInt();
        sc.nextLine();

        produtos.remove(ID);
        List<Object> produto = new ArrayList<>();
        //refatorar os IDs
        for (int i = 0; i < produtos.size(); i++) {
            produto.add(produtos.get(i));

            if(!produto.isEmpty()){
                System.out.println(produto);
                produto.set(0, i);
                produtos.set(i,produto);
                produto.clear();
                
            }
        }
    }

    public static void pesquisaProduto(List<Object> produtos, Scanner sc) {
        System.out.println("Digite o nome do produto a ser filtrado");
        String nomeFiltrado = sc.nextLine();
        List<Object> produto = new ArrayList<>();
        List<Object> produtosFiltrados = new ArrayList<>();

        for (int i = 0; i < produtos.size(); i++) {
            produto.add(produtos.get(i));
            if(produto.get(1).equals(nomeFiltrado)){
                produtosFiltrados.add(produto);
                produto.clear();
            }
        }
        System.out.println(produtosFiltrados);
    }
}
