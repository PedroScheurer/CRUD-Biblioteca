import java.util.List;
import java.util.Scanner;

// Classe Main: View/UI
public class Main {
    // Dependencias (Atributos do metodo main, classe que não é classe)
    private static Biblioteca biblioteca = new Biblioteca();
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        String menu = """
                === Sistema Biblioteca ===
                Escolha uma das opções abaixo:
                1 - Adicionar Livro
                2 - Listar Acervo
                3 - Remover Livro
                4 - Buscar Livro
                5 - Atualizar Livro
                0 - Sair
                """;

        int opcao;
        do {
            System.out.println(menu);
            opcao = Input.scanInt("Digite uma opção: ", scan);
            try {
                switch (opcao) {
                    case 1:
                        cadastrarLivro();
                        System.out.println("Pressione enter para continuar");
                        scan.nextLine();
                        break;

                    case 2:
                        listarLivros();
                        System.out.println("Pressione enter para continuar");
                        scan.nextLine();
                        break;

                    case 3:
                        removerLivro();
                        System.out.println("Pressione enter para continuar");
                        scan.nextLine();
                        break;

                    case 4:
                        buscarLivro();
                        System.out.println("Pressione enter para continuar");
                        scan.nextLine();
                        break;

                    case 5:
                        atualizarLivro();
                        System.out.println("Pressione enter para continuar");
                        scan.nextLine();
                        break;

                    case 0:
                        System.out.println("Saindo");
                        break;

                    default:
                        System.out.println("Opção inválida");
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (opcao != 0);
    }

    private static void cadastrarLivro() {
        try {
            String titulo = Input.scanString("Digite o titulo: ", scan);
            String autor = Input.scanString("Digite o autor: ", scan);
            int anoPublicacao = Input.scanInt("Digite o ano de publicação: ", scan);
            int numeroPaginas = Input.scanInt("Digite o número de páginas: ", scan);

            Livro novoLivro = new Livro(titulo, autor, anoPublicacao, numeroPaginas);

            Livro livroAdicionado = biblioteca.adicionarLivro(novoLivro);
            System.out.println("\nLivro adicionado com sucesso: " + livroAdicionado + "\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void atualizarLivro() {
        try {
            listarLivros();
            if (biblioteca.contagemLivros() == 0) {
                return;
            }
            System.out.println("\nDigite 0 (zero) se não quiser atualizar o campo!");
            int indice = Input.scanInt("Indice: ", scan);
            String titulo = Input.scanString("Titulo: ", scan);
            String autor = Input.scanString("Autor: ", scan);
            int anoPublicacao = Input.scanInt("Ano de publicação: ", scan);
            int numeroPaginas = Input.scanInt("Número de páginas: ", scan);

            Livro livroAtualizado = biblioteca.atualizarLivro(indice - 1, titulo, autor, anoPublicacao, numeroPaginas);

            System.out.println("Livro atualizado com sucesso: " + livroAtualizado + "\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private static void listarLivros() {
        try {
            List<Livro> acervo = biblioteca.pesquisarLivro();
            imprimirLista(acervo);
            mostrarQuantidade();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void removerLivro() {
        try {
            listarLivros();
            if (biblioteca.contagemLivros() == 0) {
                return;
            }
            int indice = Input.scanInt("Digite o indice do livro: ", scan);

            Livro livroRemovido = biblioteca.removerLivro(indice - 1);
            System.out.println("Livro removido com sucesso: " + livroRemovido + "\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void buscarLivro() {
        try {
            List<Livro> livrosBuscados = null;
            String menu = """
                    === Pesquisa ===
                    1 - Titulo
                    2 - Titulo e Autor
                    3 - Intervalo de Anos
                    4 - Mais Antigo e Mais Novo
                    """;
            System.out.println(menu);
            int opcao = Input.scanInt("Digite a opção:", scan);

            switch (opcao) {
                case 1:
                    String titulo = Input.scanString("Digite o titulo: ", scan);
                    livrosBuscados = biblioteca.pesquisarLivro(titulo);
                    break;
                case 2:
                    titulo = Input.scanString("Digite o titulo: ", scan);
                    String autor = Input.scanString("Digite o nome do autor: ", scan);
                    livrosBuscados = biblioteca.pesquisarLivro(titulo, autor);
                    break;
                case 3:
                    int anoInicial = Input.scanInt("Ano Incial: ", scan);
                    int anoFinal = Input.scanInt("Ano Final: ", scan);
                    livrosBuscados = biblioteca.pesquisarLivro(anoInicial, anoFinal);
                    break;
                case 4:
                    livrosBuscados = biblioteca.pesquisarAntigos();
                    livrosBuscados.addAll(biblioteca.pesquisarAtuais());
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }
            if (livrosBuscados != null) {
                imprimirLista(livrosBuscados);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void imprimirLista(List<Livro> acervo) {
        System.out.println("Livros Encontrados: \n");
        for (int i = 0; i < acervo.size(); i++) {
            System.out.println((i + 1) + " - " + acervo.get(i) + "\n");
        }
    }

    private static void mostrarQuantidade() {
        try {
            System.out.println("Quantidade Total: " + biblioteca.contagemLivros());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}