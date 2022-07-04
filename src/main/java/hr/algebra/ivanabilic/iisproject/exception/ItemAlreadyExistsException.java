package hr.algebra.ivanabilic.iisproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "Item already exists.")
public class ItemAlreadyExistsException extends Exception{
}
