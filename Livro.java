// Classe Livro: Entidade

import java.time.LocalDate;

public abstract class Livro {
    private String titulo;
    private String autor;
    private int anoPublicacao;
    private int numeroPaginas;

    public Livro() {

    }

    public Livro(String titulo, String autor, int anoPublicacao, int numeroPaginas) {
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.numeroPaginas = numeroPaginas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    @Override
    public String toString() {
        return "Titulo: " + titulo + ", Autor: " + autor + ", Ano de Publicacao: " + anoPublicacao
                + ", Numero de Paginas: " + numeroPaginas + ", Formato: " + this.getTipoLivro();
    }

    // FORÇA as classes filhas à sobrescrever esse metodo
    public abstract String getTipoLivro();

    // Impede que as classes filhas sobrescrevam esse metodo
    public final int getTempoPublicacao() {
        int anoAtual = LocalDate.now().getYear();
        return anoAtual - this.anoPublicacao;
    }

    public void atualizar(String titulo, String autor, int anoPublicacao, int numeroPaginas, int anoMinimo) {
        if (!titulo.trim().isBlank() && !titulo.trim().equals("0")) {
            this.setTitulo(titulo);
        }
        if (!autor.trim().isBlank() && !autor.trim().equals("0")) {
            this.setAutor(autor);
        }
        if (anoPublicacao >= anoMinimo) {
            this.setAnoPublicacao(anoPublicacao);
        }
        if (numeroPaginas > 0) {
            this.setNumeroPaginas(numeroPaginas);
        }
    }
}
