package io.github.felipeszfd.libraryapi.repository;

import io.github.felipeszfd.libraryapi.model.Autor;
import io.github.felipeszfd.libraryapi.model.GeneroLivro;
import io.github.felipeszfd.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class LivroRepositoryTest {

    @Autowired
    LivroRepository livroRepository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarTest() {
        Livro livro = new Livro();
        livro.setIsbn("12345");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Outro Livro");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        Autor autor = autorRepository.findById(UUID.fromString("13903be3-653e-4c56-b9f3-d60bd02a1115")).orElse(null);

        livro.setAutor(autor);

        livroRepository.save(livro);

    }

    @Test
    void salvarCascadeTest() { //Raro usar cascade
        Livro livro = new Livro();
        livro.setIsbn("12345");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Outro Livro");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        Autor autor = new Autor();
        autor.setNome("Felipe");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(2001, 11, 29));

        livro.setAutor(autor);

        livroRepository.save(livro);
    }

    @Test
    void salvarAutorELivroTest() { //Raro usar cascade
        Livro livro = new Livro();
        livro.setIsbn("12345");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Terceiro Livro");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        Autor autor = new Autor();
        autor.setNome("Felipe");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(2001, 11, 29));

        autorRepository.save(autor);

        livro.setAutor(autor);

        livroRepository.save(livro);
    }

    @Test
    void atualizarAutorDoLivro() {
        var livroParaAtualizar = livroRepository.findById(UUID.fromString("6c7b4ef6-bffe-4d43-9a31-76c5712383bd")).orElse(null);
        Autor autor = autorRepository.findById(UUID.fromString("b8a30f89-0770-41e5-9613-27c1fb1ddd73")).orElse(null);

        livroParaAtualizar.setAutor(autor);

        livroRepository.save(livroParaAtualizar);
    }

    @Test
    void deletar() {
        UUID id = UUID.fromString("6c7b4ef6-bffe-4d43-9a31-76c5712383bd");
        livroRepository.deleteById(id);
    }

    @Test
    void deletarCascade() {
        UUID id = UUID.fromString("6c7b4ef6-bffe-4d43-9a31-76c5712383bd");
        livroRepository.deleteById(id);
    }

    @Test
    @Transactional
    void buscarLivroTest() {
        UUID id = UUID.fromString("");
        Livro livro = livroRepository.findById(id).orElse(null);
        System.out.println("Livro: ");
        System.out.println(livro.getTitulo());
        System.out.println("Autor: ");
        System.out.println(livro.getAutor().getNome());
    }

    @Test
    void pesquisaPorTituloTest(){
        List<Livro> lista = livroRepository.findByTitulo("O roubo da casa assombrada");
        lista.forEach(System.out::println);
    }


    @Test
    void pesquisaPorISBNTest(){
        List<Livro> lista = livroRepository.findByIsbn("20847-84874");
        lista.forEach(System.out::println);
    }


    @Test
    void pesquisaPorTituloEPrecoTest(){
        var preco = BigDecimal.valueOf(204.00);
        var tituloPesquisa = "O roubo da casa assombrada";

        List<Livro> lista = livroRepository.findByTituloAndPreco(tituloPesquisa, preco);
        lista.forEach(System.out::println);
    }

    @Test
    void listarLivrosComQueryJPQL(){
        var resultado = livroRepository.listarTodosOrdenadoPorTituloAndPreco();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarAutoresDosLivros(){
        var resultado = livroRepository.listarAutoresDosLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarTitulosNaoRepetidosDosLivros(){
        var resultado = livroRepository.listarNomesDiferentesLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarGenerosDeLivrosAutoresBrasileiros(){
        var resultado = livroRepository.listarGenerosAutoresBrasileiros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroQueryParamTest(){
        var resultado = livroRepository.findByGenero(GeneroLivro.MISTERIO, "preco");
        resultado.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroPositionalParamTest(){
        var resultado = livroRepository.findByGeneroPositionalParameters("preco", GeneroLivro.MISTERIO);
        resultado.forEach(System.out::println);
    }

    @Test
    void deletePorGeneroTest(){
        livroRepository.deleteByGenero(GeneroLivro.CIENCIA);
    }

    @Test
    void updateDataPublicacaoTest(){
        livroRepository.updateDataPublicacao(LocalDate.of(2000,1,1));
    }
}
