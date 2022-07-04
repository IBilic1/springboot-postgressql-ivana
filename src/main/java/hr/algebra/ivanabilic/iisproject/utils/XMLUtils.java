package hr.algebra.ivanabilic.iisproject.utils;

import com.thaiopensource.validate.SchemaReader;
import com.thaiopensource.validate.ValidationDriver;
import com.thaiopensource.validate.auto.AutoSchemaReader;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class XMLUtils {

    public static <T> T convert(Class<T> clazz, InputStream input) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        T temp = (T) jaxbUnmarshaller.unmarshal(input);
        return temp;
    }

    public static <T> boolean validate(String xsdPath, MultipartFile file) {

        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(file.getInputStream()));
            return true;
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static <T> boolean validateWithRng(String rngPath, MultipartFile file) {

        try {

            SchemaReader sr = new AutoSchemaReader();
            ValidationDriver driver = new ValidationDriver(sr);
            InputSource inRng = ValidationDriver.fileInputSource(rngPath);
            inRng.setEncoding("UTF-8");
            driver.loadSchema(inRng);
            return driver.validate(new InputSource(file.getInputStream()));
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
