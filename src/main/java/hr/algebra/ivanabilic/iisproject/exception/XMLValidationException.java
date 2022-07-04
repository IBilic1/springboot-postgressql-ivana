package hr.algebra.ivanabilic.iisproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY, reason = "XML Validation exception")
public class XMLValidationException extends Exception{
}
