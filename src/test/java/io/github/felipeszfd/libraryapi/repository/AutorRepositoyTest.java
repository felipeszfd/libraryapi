package io.github.felipeszfd.libraryapi.repository;

import io.github.felipeszfd.libraryapi.model.Autor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoyTest {

    @Autowired
    AutorRepository autorRepository;

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
}
