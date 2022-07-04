package hr.algebra.ivanabilic.iisproject.service;

import hr.algebra.ivanabilic.iisproject.UtilsTemo;
import hr.algebra.ivanabilic.iisproject.entity.FileSkeleton;
import hr.algebra.ivanabilic.iisproject.exception.ItemNotFoundException;
import hr.algebra.ivanabilic.iisproject.exception.XMLValidationException;
import hr.algebra.ivanabilic.iisproject.repository.AuthRepository;
import hr.algebra.ivanabilic.iisproject.repository.PictureRepository;
import hr.algebra.ivanabilic.iisproject.utils.FileHendler;
import hr.algebra.ivanabilic.iisproject.utils.XMLUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBException;
import java.io.*;

@Service
public class PictureServiceImpl implements PictureService {

    private PictureRepository repository;
    private AuthRepository authRepository;

    public static final String BASE_URL = "./pictures/";
    public static final String XSD_PATH = "../xml_validation/Picture.xsd";
    public static final String RNG_PATH = "../xml_validation/Picture.rng";

    public PictureServiceImpl(PictureRepository repository, AuthRepository authRepository) {
        this.repository = repository;
        this.authRepository = authRepository;
    }

    @Override
    public FileSkeleton savePicture(MultipartFile file, int typeOfValidation) throws IOException, JAXBException, XMLValidationException {
        if (!chooseValidation(file, typeOfValidation)) {
            throw new XMLValidationException();
        }

        FileSkeleton convert = UtilsTemo.convert(FileSkeleton.class, file.getInputStream());

        FileSkeleton picture = new FileSkeleton(convert.getFileName(), convert.getFileType(), convert.getContentLength(), convert.getData());
        FileSkeleton save = repository.save(picture);

        System.out.println(picture.getFileType());

        String type = convert.getFileType().substring(convert.getFileType().lastIndexOf("/") + 1);
        String path = BASE_URL + picture.id + "." + type;

        FileHendler.createFile(path);
        FileHendler.writeInFile(path, convert.getData());

        return save;
    }

    @Override
    public byte[] getPicture(String fileId) throws IOException, JAXBException, ItemNotFoundException {
        FileSkeleton skeleton = repository.findById(fileId).orElseThrow(() -> new ItemNotFoundException());
        System.out.println(BASE_URL+fileId+"."+ FileHendler.parseExtension(skeleton.getFileType()));

        byte[] array = new byte[(int) skeleton.getContentLength()];
        InputStream is = new FileInputStream(BASE_URL+fileId+"."+ FileHendler.parseExtension(skeleton.getFileType()));
        try(DataInputStream os=new DataInputStream(is)){
           os.readFully(array);
        }

        return array;
    }


    private boolean chooseValidation(MultipartFile file, int typeOfValidation) throws IOException, JAXBException {
        switch (typeOfValidation) {
            case 1:
                return XMLUtils.validate(XSD_PATH, file);
            case 2:
                return XMLUtils.validateWithRng(RNG_PATH, file);
            default:
                return XMLUtils.validate(XSD_PATH, file);
        }

    }
}
