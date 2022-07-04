package hr.algebra.ivanabilic.iisproject.service;

import hr.algebra.ivanabilic.iisproject.entity.FileSkeleton;
import hr.algebra.ivanabilic.iisproject.exception.ItemNotFoundException;
import hr.algebra.ivanabilic.iisproject.exception.XMLValidationException;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface PictureService {
//komentatiii...
    FileSkeleton savePicture(MultipartFile file, int typeOfValidation) throws IOException, JAXBException, XMLValidationException;
    byte[] getPicture(String fileId) throws IOException, JAXBException, ItemNotFoundException;




}
