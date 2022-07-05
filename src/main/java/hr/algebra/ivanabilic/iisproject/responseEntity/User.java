package hr.algebra.ivanabilic.iisproject.responseEntity;

import hr.algebra.ivanabilic.iisproject.entity.AppUser;
import lombok.Getter;
import lombok.Setter;

public class User {

    @Getter
    @Setter
    private String id;
    @Getter
    private String username;
    @Getter
    private String password;

    public User(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public static User convert(AppUser user){
        return new User(user.getId(),user.getUsername(),user.getPassword());
    }
    public static AppUser convert(User user){
        return new AppUser(user.getUsername(),user.getPassword());
    }

}
