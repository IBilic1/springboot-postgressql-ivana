package hr.algebra.ivanabilic.iisproject.responseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePicture {
    private String fileId;
    private String fileName;
    private String downloadUri;
    private String fileType;
    private long fileSize;
}
