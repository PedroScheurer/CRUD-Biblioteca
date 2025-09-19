import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Classe Biblioteca: Service(Negócio)
public class Biblioteca {
    private List<Livro> acervo;

    public Biblioteca() {
        this.acervo = new ArrayList<>();
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

    public Livro atualizarLivro(int indice, String titulo, String autor, int anoPublicacao, int numeroPaginas)
            throws Exception {
        Livro livro;
        try {
            livro = pesquisarLivro(indice);
            if (validarTituloDuplicado(titulo) && 
            validarAutorDuplicado(autor) &&
            validarAnoDuplicado(anoPublicacao)) {
                throw new Exception("Livro já cadastrado");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        if (!titulo.trim().isBlank() && !titulo.trim().equals("0")) {
            livro.setTitulo(titulo);
        }
        if (!autor.trim().isBlank() && !autor.trim().equals("0")) {
            livro.setAutor(autor);
        }
        if (anoPublicacao > 0) {
            livro.setAnoPublicacao(anoPublicacao);
        }
        if (numeroPaginas > 0) {
            livro.setNumeroPaginas(numeroPaginas);
        }
        return livro;
    }

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
            } else {
                throw new Exception("Nenhum livro encontrado");
            }

        }
        return livrosEncontrados;
    }

    public List<Livro> pesquisarLivro(String titulo, String autor) throws Exception {
        List<Livro> livrosEncontrados = new ArrayList<>();
        for (Livro livro : acervo) {
            if (livro.getTitulo().toUpperCase().contains(titulo.toUpperCase()) &&
                    livro.getAutor().toUpperCase().contains(autor.toUpperCase())) {
                livrosEncontrados.add(livro);
            } else {
                throw new Exception("Nenhum livro encontrado");
            }

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
                livrosEncontrados.add(maisAntigo);
            }
        }
        for (int i = 0; i < acervo.size(); i++) {
            if (acervo.get(i).getAnoPublicacao() == maisAntigo.getAnoPublicacao() &&
                    acervo.get(i) != maisAntigo) {
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
                livrosEncontrados.add(maisNovo);
            }
        }
        for (int i = 0; i < acervo.size(); i++) {
            if (acervo.get(i).getAnoPublicacao() == maisNovo.getAnoPublicacao() &&
                    acervo.get(i) != maisNovo) {
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
            throw new Exception("Autor não poder ser em branco");
        }
        int anoAtual = LocalDate.now().getYear();
        if (livro.getAnoPublicacao() < 1900 || livro.getAnoPublicacao() > anoAtual) {
            throw new Exception("Ano de publicação inválido");
        }

        if (livro.getNumeroPaginas() <= 0) {
            throw new Exception("Número de páginas inválido");
        }
        if (validarTituloDuplicado(livro.getTitulo()) &&
                validarAutorDuplicado(livro.getAutor()) &&
                validarAnoDuplicado(livro.getAnoPublicacao())) {
            throw new Exception("Livro já cadastrado");
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
