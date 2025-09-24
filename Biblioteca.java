import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Classe Biblioteca: Service(Negócio)
public class Biblioteca {
    private static final int ANO_MINIMO_PUBLICACAO = 1900;

    private List<Livro> acervo;

    public Biblioteca() {
        this.acervo = new ArrayList<>();
    }

    public static int getAnoMinimoPublicacao() {
        return ANO_MINIMO_PUBLICACAO;
    }

    public Livro adicionarLivro(Livro livro) throws Exception {
        try {
            validarLivro(livro);
            acervo.add(livro);
            return livro;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    // public Livro atualizarLivro(int indice, String titulo, String autor, int anoPublicacao, int numeroPaginas,
    //         int numeroExemplares, String dimensoes) throws Exception {
    //     Livro livro;
    //     try {
    //         livro = pesquisarLivro(indice);
    //         if (validarTituloDuplicado(titulo) &&
    //                 validarAutorDuplicado(autor) &&
    //                 validarAnoDuplicado(anoPublicacao)) {
    //             throw new Exception("Livro já cadastrado");
    //         }
    //         validarAtualizacao(livro, indice, titulo, autor, anoPublicacao, numeroPaginas);

    //         // Cast para salvar variavel com o tipo LivroFisico e atualizar dados desse tipo
    //         if (livro instanceof LivroFisico) {
    //             LivroFisico livroFisico = (LivroFisico) livro;

    //             if (numeroExemplares > 0) {
    //                 livroFisico.setNumeroExemplares(numeroExemplares);
    //             }
    //             if (!dimensoes.trim().isBlank() && !dimensoes.trim().equals("0")) {
    //                 livroFisico.setDimensoes(dimensoes);
    //             }
    //         }
    //     } catch (Exception e) {
    //         throw new Exception(e.getMessage());
    //     }

    //     return livro;
    // }

    public Livro atualizarLivro(int indice, String titulo, String autor, int anoPublicacao, int numeroPaginas,
            int numeroExemplares, String dimensoes, String formatoArquivo, double tamanhoArquivo) throws Exception {
        Livro livro = pesquisarLivro(indice);
        try {
            if (validarTituloDuplicado(titulo) &&
                    validarAutorDuplicado(autor) &&
                    validarAnoDuplicado(anoPublicacao)) {
                throw new Exception("Livro já cadastrado");
            }

            if (livro instanceof LivroFisico fisico) {
                fisico.atualizar(titulo, autor, anoPublicacao, numeroPaginas, ANO_MINIMO_PUBLICACAO,
                        numeroExemplares, dimensoes);
            } else if (livro instanceof LivroDigital digital) {
                digital.atualizar(titulo, autor, anoPublicacao, numeroPaginas, ANO_MINIMO_PUBLICACAO,
                        formatoArquivo, tamanhoArquivo);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return livro;
    }

    // private void validarAtualizacao(Livro livro, int indice, String titulo, String autor, int anoPublicacao,
    //         int numeroPaginas) {

    //     if (!titulo.trim().isBlank() && !titulo.trim().equals("0")) {
    //         livro.setTitulo(titulo);
    //     }
    //     if (!autor.trim().isBlank() && !autor.trim().equals("0")) {
    //         livro.setAutor(autor);
    //     }
    //     if (anoPublicacao >= ANO_MINIMO_PUBLICACAO) {
    //         livro.setAnoPublicacao(anoPublicacao);
    //     }
    //     if (numeroPaginas > 0) {
    //         livro.setNumeroPaginas(numeroPaginas);
    //     }
    // }

    public Livro removerLivro(int indice) throws Exception {
        Livro resultadoLivroPesquisado;
        try {
            resultadoLivroPesquisado = pesquisarLivro(indice);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        acervo.remove(resultadoLivroPesquisado);
        return resultadoLivroPesquisado;
    }

    public Livro pesquisarLivro(int indice) throws Exception {
        if (acervo.get(indice) != null || indice >= 0) {
            return acervo.get(indice);
        } else {
            throw new Exception("Nenhum livro encontrado");
        }
    }

    public List<Livro> pesquisarLivro(int anoInicial, int anoFinal) throws Exception {
        if (anoInicial > anoFinal) {
            throw new Exception("Data inicial nao pode ser menor que data final");
        }
        List<Livro> livrosEncontrados = new ArrayList<>();
        for (Livro livro : acervo) {
            if (livro.getAnoPublicacao() >= anoInicial && livro.getAnoPublicacao() <= anoFinal) {
                livrosEncontrados.add(livro);
            }
        }
        if (livrosEncontrados.isEmpty()) {
            throw new Exception("Nenhum livro encontrado");
        }

        return livrosEncontrados;
    }

    public List<Livro> pesquisarLivro(String titulo) throws Exception {
        List<Livro> livrosEncontrados = new ArrayList<>();
        for (Livro livro : acervo) {
            if (livro.getTitulo().toUpperCase().contains(titulo.toUpperCase())) {
                livrosEncontrados.add(livro);
            }
        }
        if (livrosEncontrados.isEmpty()) {
            throw new Exception("Nenhum livro encontrado");
        }
        return livrosEncontrados;
    }

    public List<Livro> pesquisarLivro(String titulo, String autor) throws Exception {
        List<Livro> livrosEncontrados = new ArrayList<>();
        for (Livro livro : acervo) {
            if (livro.getTitulo().toUpperCase().contains(titulo.toUpperCase()) &&
                    livro.getAutor().toUpperCase().contains(autor.toUpperCase())) {
                livrosEncontrados.add(livro);
            }
        }

        if (livrosEncontrados.isEmpty()) {
            throw new Exception("Nenhum livro encontrado");
        }
        return livrosEncontrados;
    }

    public List<Livro> pesquisarLivro() throws Exception {
        if (acervo.isEmpty()) {
            throw new Exception("Acervo vazio");
        }
        return acervo;
    }

    public List<Livro> pesquisarAntigos() {
        List<Livro> livrosEncontrados = new ArrayList<>();
        Livro maisAntigo = acervo.get(0);
        for (int i = 0; i < acervo.size(); i++) {
            if (acervo.get(i).getAnoPublicacao() < maisAntigo.getAnoPublicacao()) {
                maisAntigo = acervo.get(i);
            }
        }
        for (int i = 0; i < acervo.size(); i++) {
            if (acervo.get(i).getAnoPublicacao() == maisAntigo.getAnoPublicacao()) {
                livrosEncontrados.add(acervo.get(i));
            }
        }
        return livrosEncontrados;
    }

    public List<Livro> pesquisarAtuais() {
        List<Livro> livrosEncontrados = new ArrayList<>();
        Livro maisNovo = acervo.get(0);
        for (int i = 0; i < acervo.size(); i++) {
            if (acervo.get(i).getAnoPublicacao() > maisNovo.getAnoPublicacao()) {
                maisNovo = acervo.get(i);
            }
        }
        for (int i = 0; i < acervo.size(); i++) {
            if (acervo.get(i).getAnoPublicacao() == maisNovo.getAnoPublicacao()) {
                livrosEncontrados.add(acervo.get(i));
            }
        }
        return livrosEncontrados;
    }

    public int contagemLivros() throws Exception {
        return acervo.size();
    }

    private boolean validarLivro(Livro livro) throws Exception {
        if (livro == null) {
            throw new Exception("Livro não pode ser nulo");
        }
        livro.setTitulo(livro.getTitulo().trim());
        if (livro.getTitulo() == null || livro.getTitulo().isEmpty()) {
            throw new Exception("Titulo não pode ser em branco");
        }
        livro.setAutor(livro.getAutor().trim());
        if (livro.getAutor() == null || livro.getAutor().isEmpty()) {
            throw new Exception("Autor nao poder ser em branco");
        }
        int anoAtual = LocalDate.now().getYear();
        if (livro.getAnoPublicacao() < ANO_MINIMO_PUBLICACAO || livro.getAnoPublicacao() > anoAtual) {
            throw new Exception("Ano de publicação invalido");
        }

        if (livro.getNumeroPaginas() <= 0) {
            throw new Exception("Número de paginas invalido");
        }
        if (validarTituloDuplicado(livro.getTitulo()) &&
                validarAutorDuplicado(livro.getAutor()) &&
                validarAnoDuplicado(livro.getAnoPublicacao())) {
            throw new Exception("Livro ja cadastrado");
        }
        return true;
    }

    // private boolean validarDuplicidade(Livro livro) {
    // for (Livro l : acervo) {
    // if (livro.getTitulo().equalsIgnoreCase(l.getTitulo()) &&
    // livro.getAutor().equalsIgnoreCase(l.getAutor()) &&
    // livro.getAnoPublicacao() == l.getAnoPublicacao()) {
    // return true;
    // }
    // }
    // return false;
    // }

    private boolean validarTituloDuplicado(String titulo) {
        for (Livro livro : acervo) {
            if (titulo.equalsIgnoreCase(livro.getTitulo())) {
                return true;
            }
        }
        return false;
    }

    private boolean validarAutorDuplicado(String autor) {
        for (Livro livro : acervo) {
            if (autor.equalsIgnoreCase(livro.getAutor())) {
                return true;
            }
        }
        return false;
    }

    private boolean validarAnoDuplicado(int ano) {
        for (Livro livro : acervo) {
            if (ano == livro.getAnoPublicacao()) {
                return true;
            }
        }
        return false;
    }
}
