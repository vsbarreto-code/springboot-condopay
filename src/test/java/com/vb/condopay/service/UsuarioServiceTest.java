package com.vb.condopay.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import com.vb.condopay.database.entity.UsuarioModel;
import com.vb.condopay.database.entity.enums.UsuarioRole;
import com.vb.condopay.database.repository.IUsuarioRepository;
import com.vb.condopay.dto.request.UsuarioRequestDto;
import com.vb.condopay.dto.response.UsuarioResponseDto;
import com.vb.condopay.exception.handler.ConflitoException;
import com.vb.condopay.exception.handler.NaoEncontradoException;
import com.vb.condopay.mapper.IUsuarioMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private IUsuarioRepository repository;

    @Mock
    private IUsuarioMapper mapper;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UsuarioService service;

    @Nested
    class cadastrarUsuario {
        @Test
        @DisplayName("Cadastrar o usuário e retornar um sucesso")
        void cadastrarUsuarioSucesso() {
            //ARRAGE -> Organizar
            var request = new UsuarioRequestDto("Victor", "victor@gmail.com", "senha123", UsuarioRole.MORADOR);

            var entity = new UsuarioModel();
            var entitySalvo = new UsuarioModel();

            UUID id = UUID.randomUUID();
            var senhaHash = encoder.encode(request.senha());

            entitySalvo.setId(id);
            entitySalvo.setSenha(senhaHash);

            var response = new UsuarioResponseDto(id, "Victor", "victor@gmail.com", UsuarioRole.MORADOR, true,
                                                  LocalDateTime.now());

            doReturn(false).when(repository)
                           .existsByEmail(request.email());
            doReturn(entity).when(mapper)
                            .toEntity(request);
            doReturn(entitySalvo).when(repository)
                                 .save(entity);
            doReturn(response).when(mapper)
                              .toResponse(entitySalvo);

            //ACT -> Agir
            var output = service.cadastrarUsuario(request);

            //ASSERT -> Afirmar
            assertNotNull(output);
            assertEquals(response.id(), output.id());
            verify(repository, times(1)).save(any());
        }

        @Test
        @DisplayName("Cadastrar o usuário e dar conflito/erro em relacao ao email do usuário")
        void cadastrarUsuarioEmailConflito() {

            //ARRAGE -> Organizar
            var request = new UsuarioRequestDto("Victor", "victor@gmail.com", "senha123", UsuarioRole.MORADOR);

            doReturn(true).when(repository)
                          .existsByEmail(request.email());

            //ACT -> Agir
            var exception = assertThrows(ConflitoException.class, () -> service.cadastrarUsuario(request));

            //ASSERT -> Afirmar
            assertEquals("EMAIL JÁ CADASTRADO NO SISTEMA, TENTE NOVAMENTE!!", exception.getMessage());
            verify(repository, never()).save(any());
        }
    }

    @Nested
    class listarUsuario {
        @Test
        @DisplayName("Listar todos os usuários cadastrados no banco de dados com sucesso")
        void listarUsuarioSucesso() {
            // Arrange
            UUID id = UUID.randomUUID();

            var entity = new UsuarioModel();
            entity.setId(id);
            entity.setNome("Victor");
            entity.setEmail("victor@gmail.com");
            entity.setRole(UsuarioRole.MORADOR);
            entity.setAtivo(true);
            entity.setCriadoEm(LocalDateTime.now());

            List<UsuarioModel> listar = List.of(entity);

            doReturn(listar).when(repository)
                            .findAll();

            // Act
            var output = service.listarUsuario();

            // Assert
            assertNotNull(output);
            assertFalse(output.isEmpty());
            assertEquals(1, output.size());
            assertEquals("Victor", output.get(0)
                                         .nome());
            assertEquals("victor@gmail.com", output.get(0)
                                                   .email());

            verify(repository, times(1)).findAll();
        }
    }

    @Nested
    class buscarUsuario {
        @Test
        @DisplayName("Buscar usuario via ID com sucesso")
        void buscarUsuarioSucesso() {
            //ARRANGE
            UUID id = UUID.randomUUID();
            var entity = new UsuarioModel();
            var response = new UsuarioResponseDto(id, "Victor", "victor@gmail.com", UsuarioRole.MORADOR, true,
                                                  LocalDateTime.now());

            doReturn(Optional.of(entity)).when(repository)
                                         .findById(id);
            doReturn(response).when(mapper)
                              .toResponse(entity);

            //ACT
            var output = service.buscarUsuario(id);

            //ASSERT
            assertNotNull(output);
            assertEquals(response.id(), output.id());
            verify(repository, times(1)).findById(id);
            verify(mapper, times(1)).toResponse(entity);
        }

        @Test
        @DisplayName("Buscar usuário pelo ID com erro Não Encontrado")
        void buscarUsuarioIdNaoEncontrado() {
            UUID id = UUID.randomUUID();

            doReturn(Optional.empty()).when(repository)
                                      .findById(id);

            assertThrows(NaoEncontradoException.class, () -> service.buscarUsuario(id));
            verify(repository, times(1)).findById(id);
            verify(mapper, never()).toResponse(any());
        }
    }

    @Nested
    class usuarioLogado {
        @Test
        @DisplayName("Buscar os dados do usuario logado e retornar com sucesso")
        void usuarioLogado() {
            //ARRANGE
            UUID id = UUID.randomUUID();
            String email = "victor@gmail.com";
            var entity = new UsuarioModel();
            var response = new UsuarioResponseDto(id, "Victor", email, UsuarioRole.MORADOR, true,
                                                  LocalDateTime.now());

            Jwt jwt = mock(Jwt.class);
            doReturn(email).when(jwt)
                           .getSubject();
            doReturn(Optional.of(entity)).when(repository)
                                         .findByEmail(email);
            doReturn(response).when(mapper)
                              .toResponse(entity);

            //ACT
            var output = service.usuarioLogado(jwt);

            //ASSERT
            assertNotNull(output);
            assertEquals(response.id(), output.id());
            assertEquals(response.email(), output.email());
            verify(jwt, times(1)).getSubject();
            verify(repository, times(1)).findByEmail(email);
            verify(mapper, times(1)).toResponse(entity);
        }

        @Test
        @DisplayName("Buscar usuário logado - não encontrado")
        void usuarioLogadoNaoEncontrado() {
            // ARRANGE
            String email = "inexistente@gmail.com";

            Jwt jwt = mock(Jwt.class);
            doReturn(email).when(jwt)
                           .getSubject();
            doReturn(Optional.empty()).when(repository)
                                      .findByEmail(email);

            // ACT + ASSERT
            assertThrows(NaoEncontradoException.class, () -> service.usuarioLogado(jwt));
            verify(jwt, times(1)).getSubject();
            verify(repository, times(1)).findByEmail(email);
            verify(mapper, never()).toResponse(any());
        }
    }
}
