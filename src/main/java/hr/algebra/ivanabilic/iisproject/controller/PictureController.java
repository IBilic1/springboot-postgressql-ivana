package hr.algebra.ivanabilic.iisproject.controller;

import hr.algebra.ivanabilic.iisproject.entity.FileSkeleton;
import hr.algebra.ivanabilic.iisproject.exception.ItemNotFoundException;
import hr.algebra.ivanabilic.iisproject.exception.XMLValidationException;
import hr.algebra.ivanabilic.iisproject.responseEntity.ResponsePicture;
import hr.algebra.ivanabilic.iisproject.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@RestController
@RequestMapping(path = "picture")
public class PictureController {

    private PictureService pictureService;

    @Autowired
    public PictureController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @PostMapping("/upload/{typeOfValidation}")
    public ResponsePicture upload(@RequestParam("file") MultipartFile file, @PathVariable("typeOfValidation") int typeOfValidation) throws JAXBException, IOException, XMLValidationException {
        return getResponsePicture(file, typeOfValidation);
    }

    private ResponsePicture getResponsePicture(MultipartFile file, int typeOfValidation) throws IOException, JAXBException, XMLValidationException {
        FileSkeleton picture = pictureService.savePicture(file, typeOfValidation);
        String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("picture/download/")
                .path(picture.id)
                .toUriString();
        System.out.println(downloadUrl);
        return new ResponsePicture(picture.id, picture.getFileName(), downloadUrl, file.getContentType(), file.getSize());
    }


    @GetMapping(
            value = "/download/{fileId}",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public byte[] upload(@PathVariable("fileId") String fileId) throws JAXBException, IOException, ItemNotFoundException {
        return pictureService.getPicture(fileId);
    }

}
