package com.otto.fitIA.repository;

import com.otto.fitIA.models.Usuario;
import com.otto.fitIA.projection.UserDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);;
    boolean existsByEmail(String email);;

    @Query(nativeQuery = true, value = """
          SELECT usuario_db.email AS username, 
                 usuario_db.senha AS password, 
                 role_tb.id AS roleId, 
                 role_tb.authority AS authority
          FROM usuario_db
          INNER JOIN usuario_role_tb ON usuario_db.id = usuario_role_tb.usuario_id
          INNER JOIN role_tb ON role_tb.id = usuario_role_tb.role_id
          WHERE usuario_db.email = :email
       """)
    List<UserDetailsProjection> searchUserAndRolesByEmail(String email);
}
