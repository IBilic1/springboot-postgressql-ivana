package hr.algebra.ivanabilic.iisproject.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@XmlType(name = "File")
@XmlRootElement(name = "fileSkeleton")
@XmlAccessorType(XmlAccessType.FIELD)
public class FileSkeleton {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    public String id;

    private String fileName;

    private String fileType;

    private long contentLength;

    @Transient
    @Getter
    @ManyToMany(mappedBy = "user_files")
    private List<AppUser> jobs;


    public FileSkeleton(String fileName, String fileType, long contentLength, byte[] input) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.contentLength = contentLength;
        this.data = input;
    }

    @Transient
    private byte[] data;


}
