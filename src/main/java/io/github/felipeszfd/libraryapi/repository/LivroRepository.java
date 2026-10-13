package io.github.felipeszfd.libraryapi.repository;

import io.github.felipeszfd.libraryapi.model.Autor;
import io.github.felipeszfd.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    List<Livro> findByAutor(Autor autor);
}
