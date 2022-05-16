package app.igesa.metiers;
import app.igesa.dto.AccountDTO;
import app.igesa.entity.Account;
import app.igesa.entity.ChangePasswordRequest;
import app.igesa.enumerations.AccountStatus;
import app.igesa.enumerations.ErrorCode;
import app.igesa.exceptions.ResourceNotFoundException;
import app.igesa.repository.IgroupeRepository;
import app.igesa.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class AccountImp implements Iaccount {
    @Autowired
    AccountRepository userRepository;
    @Autowired
    IgroupeRepository igroupeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private static final Logger log = LoggerFactory.getLogger(AccountImp.class);


    public AccountDTO save(AccountDTO account) {
        //account.setAccountStatus(AccountStatus.PENDING);
        Account saved = userRepository.save(AccountDTO.toEntity(account));
        return AccountDTO.fromEntity(saved);
    }

    public List<AccountDTO> findAll() {
        return userRepository.findAll().stream()
                .map(AccountDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {

        if (id == null) {
            log.error("  ID IS NULL ");
            return;
        }
        userRepository.deleteById(id);
    }

    @Override
    public AccountDTO findById(Long id) {
        Optional<Account> account = userRepository.findById(id);
        AccountDTO dto = AccountDTO.fromEntity(account.get());
        return Optional.of(dto).orElseThrow(() ->
                new ResourceNotFoundException(" Aucune account avec id =" + id + " n'ete  trouver ", ErrorCode.ACCOUNT_NOT_VALID));
    }

    @Override
    public Account updateSatus(Long id, AccountStatus status) {
        Optional<Account> Data = userRepository.findById(id);
        Account saved = null;
        if (Data.isPresent()) {
            Account account = Data.get();

            if (AccountStatus.ACTIVE == status) {
                account.setAccountStatus(AccountStatus.BLOCKED);
            }
            if (AccountStatus.PENDING == status) {
                account.setAccountStatus(AccountStatus.ACTIVE);
            }
            if (AccountStatus.BLOCKED == status) {
                account.setAccountStatus(AccountStatus.PENDING);
            }

            saved = userRepository.save(account);
        }
        return saved;
    }

    @Override
    public boolean changePassword(ChangePasswordRequest request) {
        UserDetailsImpl userDetails = getIdentity();
        if (null == userDetails) {
            return false;
        }
        Optional<Account> optionalAccount = userRepository.findByUsername(userDetails.getUsername());
        if (!optionalAccount.isPresent()) {
            return false;
        }
        Account account = optionalAccount.get();
        if (passwordEncoder.matches(request.getPassword(), account.getPassword())) {
            account.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(account);
            return true;
        }
        return false;
    }

    public UserDetailsImpl getIdentity() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetailsImpl)
            return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return null;

    }

}