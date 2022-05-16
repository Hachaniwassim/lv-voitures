package app.igesa.repository;

import app.igesa.entity.Account;
import app.igesa.metiers.UserDetailsImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {


	Optional<Account> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);


    public Account findByEmail(String email);

	//public void resetPassword(String email);
}
