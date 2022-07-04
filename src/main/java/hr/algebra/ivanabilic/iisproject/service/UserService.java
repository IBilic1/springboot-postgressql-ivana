package hr.algebra.ivanabilic.iisproject.service;

import hr.algebra.ivanabilic.iisproject.entity.AppUser;
import hr.algebra.ivanabilic.iisproject.entity.FileSkeleton;
import hr.algebra.ivanabilic.iisproject.exception.ItemAlreadyExistsException;
import hr.algebra.ivanabilic.iisproject.exception.ItemNotFoundException;

import java.util.List;

public interface UserService {

    AppUser select(String username,String password) throws ItemNotFoundException;

    AppUser create(AppUser user) throws ItemAlreadyExistsException;

    void post(String user_id, FileSkeleton file) throws ItemNotFoundException;
    List<FileSkeleton> getAllFiles(String user_id) throws ItemNotFoundException;

}
