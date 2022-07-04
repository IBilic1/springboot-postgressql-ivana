package hr.algebra.ivanabilic.iisproject;

import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

public class UtilsTemo {

    public static <T> T convert(Class<T> clazz, InputStream input) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        T temp = (T) jaxbUnmarshaller.unmarshal(input);
        return temp;
    }
}
