package hr.algebra.ivanabilic.iisproject.controller;

import hr.algebra.ivanabilic.iisproject.entity.AppUser;
import hr.algebra.ivanabilic.iisproject.entity.FileSkeleton;
import hr.algebra.ivanabilic.iisproject.exception.ItemAlreadyExistsException;
import hr.algebra.ivanabilic.iisproject.exception.ItemNotFoundException;
import hr.algebra.ivanabilic.iisproject.responseEntity.ResponsePicture;
import hr.algebra.ivanabilic.iisproject.responseEntity.User;
import hr.algebra.ivanabilic.iisproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "user")
public class UserController {

    private UserService authService;

    @Autowired
    public UserController(UserService authService) {
        this.authService = authService;
    }

    @PostMapping
    public User post(@RequestBody User user) throws ItemAlreadyExistsException {
        return authService.create(user);
    }

    @PostMapping(path = "/login")
    public User login(@RequestBody User user) throws ItemNotFoundException {
        System.out.println("yey");
        return authService.select(user.getUsername(),user.getPassword());
    }

    @PostMapping(path = "/{user_id}/files")
    public void post(@PathVariable String user_id, @RequestBody FileSkeleton id_file) throws ItemNotFoundException {
        authService.post(user_id, id_file);
    }

    @GetMapping(path = "/{user_id}/files")
    public List<ResponsePicture> get(@PathVariable String user_id) throws ItemNotFoundException {
        List<FileSkeleton> pictures = authService.getAllFiles(user_id);
        String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("picture/download/").toUriString();
        return pictures.stream().map(picture -> new ResponsePicture(picture.id, picture.getFileName(), downloadUrl + picture.id, picture.getFileType(), picture.getContentLength())).collect(Collectors.toList());
    }
}
