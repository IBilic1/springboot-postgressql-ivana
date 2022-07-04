package hr.algebra.ivanabilic.iisproject.repository;

import hr.algebra.ivanabilic.iisproject.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<AppUser,String> {
    Optional<AppUser> findByUsernameAndPassword(String username,String password);
    Optional<AppUser> findByUsername(String username);
}
