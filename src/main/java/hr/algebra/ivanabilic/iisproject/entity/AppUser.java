package hr.algebra.ivanabilic.iisproject.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "user_id")
    private String id;
    @Getter
    private String username;
    @Getter
    private String password;

    @Getter
    @Setter
    private String salt;

    @ManyToMany
    @JoinTable(
            name = "user_files",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id"))
    private Set<FileSkeleton> files;


    public AppUser(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public AppUser(String username, String password) {
        this.username = username;
        this.password = password;
    }



}