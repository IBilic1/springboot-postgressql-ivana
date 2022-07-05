package hr.algebra.ivanabilic.iisproject.service;

import hr.algebra.ivanabilic.iisproject.entity.AppUser;
import hr.algebra.ivanabilic.iisproject.entity.FileSkeleton;
import hr.algebra.ivanabilic.iisproject.exception.ItemAlreadyExistsException;
import hr.algebra.ivanabilic.iisproject.exception.ItemNotFoundException;
import hr.algebra.ivanabilic.iisproject.repository.AuthRepository;
import hr.algebra.ivanabilic.iisproject.repository.PictureRepository;
import hr.algebra.ivanabilic.iisproject.responseEntity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private AuthRepository authRepository;
    private PictureRepository pictureRepository;

    @Autowired
    public UserServiceImpl(AuthRepository authRepository, PictureRepository pictureRepository) {
        this.authRepository = authRepository;
        this.pictureRepository = pictureRepository;
    }

    @Override
    public User select(String username, String password) throws ItemNotFoundException {
        Optional<AppUser> byUsername = authRepository.findByUsername(username);


        if (byUsername.isPresent()) {
            String prefix= byUsername.get().getSalt();

            String hash_pw= byUsername.get().getPassword().substring((prefix.length()));

            if (BCrypt.checkpw(password,hash_pw)) {

                return User.convert(byUsername.get());

            }
        }
        throw new ItemNotFoundException();
    }

    @Override
    public User create(User user) throws ItemAlreadyExistsException {
        String salt = BCrypt.gensalt();
        AppUser appUser = User.convert(user);
        appUser.setSalt(salt);
        appUser.setPassword(BCrypt.hashpw(user.getPassword(),salt ));
        Optional<AppUser> byUsername = authRepository.findByUsername(user.getUsername());
        if (!byUsername.isPresent()) {
            AppUser save = authRepository.save(appUser);
            user.setId(save.getId());
            return user;
        }
        throw new ItemAlreadyExistsException();
    }

    @Override
    @Transactional
    public void post(String user_id, FileSkeleton file) throws ItemNotFoundException {
        Optional<AppUser> byUsername = authRepository.findById(user_id);


        System.out.println(file.id);
        if (byUsername.isPresent()) {
            AppUser appUser = byUsername.get();
            Set<FileSkeleton> files = appUser.getFiles();

            FileSkeleton skeleton = pictureRepository.findById(file.id).get();

            files.add(skeleton);
            appUser.setFiles(files);
            authRepository.save(appUser);

        }
        throw new ItemNotFoundException();
    }

    @Override
    public List<FileSkeleton> getAllFiles(String user_id) throws ItemNotFoundException {
        List<FileSkeleton> all = pictureRepository.findAll();
        Optional<AppUser> user = authRepository.findById(user_id);
        if (user.isPresent()) {
            AppUser appUser = user.get();
            return appUser.getFiles().stream().collect(Collectors.toList());
        }
        throw new ItemNotFoundException();
    }
}
