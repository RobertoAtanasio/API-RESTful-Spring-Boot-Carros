package com.rapl.carros.api.exception;

import java.io.Serializable;
import java.nio.file.AccessDeniedException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionConfig extends ResponseEntityExceptionHandler {

//	@Autowired
//	private MessageSource messageSource;
	
	@ExceptionHandler({	EmptyResultDataAccessException.class })
	public ResponseEntity<Object> errorNotFound() {
        return ResponseEntity.notFound().build();
    }
	
	@ExceptionHandler({ AccessDeniedException.class })
	public ResponseEntity<Object> accessDenied() {
	    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Error("Acesso não permitido"));
	}

	@ExceptionHandler({ IllegalArgumentException.class })
	public ResponseEntity<Object> errorbadRequest() {
		return ResponseEntity.badRequest().build();
	}
	
//	@ExceptionHandler({ IllegalArgumentException.class })
//	public ResponseEntity<Object> handIllegalArgumentException(IllegalArgumentException ex) {
//		String mensagem = messageSource.getMessage("exception.parametro-invalido", null, LocaleContextHolder.getLocale());
//		List<Mensagem> erro = Arrays.asList(new Mensagem(mensagem));
//		return ResponseEntity.badRequest().body(erro);
//	}
//	
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException ex, 
			HttpHeaders headers, 
			HttpStatus status, 
			WebRequest request) {
		return new ResponseEntity<>(new ExceptionError("Operação não permitida"), HttpStatus.METHOD_NOT_ALLOWED);
	}
//	
//	public static class Mensagem {
//		
//		private String mensagem;
//		
//		public Mensagem(String mensagem) {
//			this.mensagem = mensagem;
//		}
//		
//		public String getMensagem() {
//			return mensagem;
//		}
//	}
}

class ExceptionError implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String error;
	
	ExceptionError(String error) {
		this.setError(error);
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
}

class Error {
	public String error;
	public Error (String error) {
		this.error = error;
	}
}

