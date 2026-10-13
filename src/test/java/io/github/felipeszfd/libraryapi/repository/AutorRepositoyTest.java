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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoyTest {

    @Autowired
    AutorRepository autorRepository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTest() {
        Autor autor = new Autor();
		autor.setNome("Maria");
		autor.setNacionalidade("Brasileira");
		autor.setDataNascimento(LocalDate.of(1960,5,31));

		var autorSalvo = autorRepository.save(autor);
		System.out.println("Autor Salvo " + autorSalvo);
    }

    @Test
    public void atualizarTest() {
        var id = UUID.fromString("79833c66-ee89-4e3e-b68f-d73cee3e7900");

        Optional<Autor> possivelAutor = autorRepository.findById(id);

        if(possivelAutor.isPresent()) {
            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados do Autor: ");
            System.out.println(autorEncontrado);

            autorEncontrado.setDataNascimento(LocalDate.of(1980, 11,29));
            autorRepository.save(autorEncontrado);
        }
    }

    @Test
    public void listarTest() {
        List<Autor> lista = autorRepository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void countTest() {
        System.out.println("Contagem de autores: " + autorRepository.count());
    }

    @Test
    public void deletePorIdTest(){
        var id = UUID.fromString("79833c66-ee89-4e3e-b68f-d73cee3e7900");
        autorRepository.deleteById(id);
    }

    @Test
    public void deleteTest(){
        var id = UUID.fromString("54974fb2-8efe-47df-9731-a133b7c6f414");
        var autor = autorRepository.findById(id).get();
        autorRepository.delete(autor);
    }

    @Test
    void salvarAutorComLivrosTest() {
        Autor autor = new Autor();
        autor.setNome("Ana");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1960,5,31));

        Livro livro = new Livro();
        livro.setIsbn("1234345");
        livro.setPreco(BigDecimal.valueOf(204));
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setTitulo("Casa Assombrada");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("12");
        livro2.setPreco(BigDecimal.valueOf(300));
        livro2.setGenero(GeneroLivro.CIENCIA);
        livro2.setTitulo("Calculo 1");
        livro2.setDataPublicacao(LocalDate.of(1980, 1, 2));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        autorRepository.save(autor);
        livroRepository.saveAll(autor.getLivros()); //Se cascade pode tirar
    }

    @Test
    void listarLivrosAutor() {
        UUID id = UUID.fromString("1cbb7f31-bf27-4090-941d-4ee5295fec60");
        var autor = autorRepository.findById(id).get();

        List<Livro> livrosLista = livroRepository.findByAutor(autor);
        autor.setLivros(livrosLista);

        autor.getLivros().forEach(System.out::println);
    }
}
