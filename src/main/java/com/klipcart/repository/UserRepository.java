
package com.klipcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.klipcart.model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    /*~~(class org.openrewrite.java.tree.J$Erroneous cannot be cast to class org.openrewrite.java.tree.J$Assignment (org.openrewrite.java.tree.J$Erroneous and org.openrewrite.java.tree.J$Assignment are in unnamed module of loader 'app'))~~>*/@Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(String email);
}