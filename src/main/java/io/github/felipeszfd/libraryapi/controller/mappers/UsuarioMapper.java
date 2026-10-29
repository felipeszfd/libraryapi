package io.github.felipeszfd.libraryapi.controller.mappers;

import io.github.felipeszfd.libraryapi.controller.dto.UsuarioDTO;
import io.github.felipeszfd.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);
}