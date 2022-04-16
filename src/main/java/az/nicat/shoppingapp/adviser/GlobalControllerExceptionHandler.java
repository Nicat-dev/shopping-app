package az.nicat.shoppingapp.adviser;

import az.nicat.shoppingapp.exception.ResourceExistsException;
import az.nicat.shoppingapp.exception.ResourceNotFoundException;
import az.nicat.shoppingapp.model.response.MetaResponse;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import static az.nicat.shoppingapp.model.enums.ESecurityMessage.*;
import static java.lang.Boolean.*;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult result = ex.getBindingResult();
        List<ObjectError> allErrors = result.getAllErrors();
        String message = String.join("\n", processAllErrors(allErrors)).toUpperCase();
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(new MetaResponse(FALSE, BAD_REQUEST.value(), message));
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(new MetaResponse(FALSE, BAD_REQUEST.value(), ex.getLocalizedMessage().toUpperCase()));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(new MetaResponse(FALSE, BAD_REQUEST.value(), ex.getLocalizedMessage().toUpperCase()));
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new MetaResponse(FALSE, NOT_FOUND.value(), "This page was not found".toUpperCase()));
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new MetaResponse(FALSE, INTERNAL_SERVER_ERROR.value(), "Something went wrong!"));
    }

//    @ExceptionHandler(FileException.class)
//    protected ResponseEntity<MetaResponse> handleFileException(FileException ex){
//        return ResponseEntity
//                .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(new MetaResponse(FALSE, INTERNAL_SERVER_ERROR.value(), ex.getLocalizedMessage()));
//    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(new MetaResponse(FALSE, BAD_REQUEST.value(), ex.getLocalizedMessage().toUpperCase()));
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(new MetaResponse(FALSE, BAD_REQUEST.value(), ex.getLocalizedMessage().toUpperCase()));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(new MetaResponse(FALSE, NOT_ACCEPTABLE.value(), ex.getLocalizedMessage().toUpperCase()));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity
                .status(UNSUPPORTED_MEDIA_TYPE)
                .body(new MetaResponse(FALSE, UNSUPPORTED_MEDIA_TYPE.value(), ex.getLocalizedMessage().toUpperCase()));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(new MetaResponse(FALSE, BAD_REQUEST.value(), ex.getLocalizedMessage().toUpperCase()));
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        HttpMethod httpMethod = Objects.requireNonNull(ex.getSupportedHttpMethods()).iterator().next();
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(new MetaResponse(FALSE, BAD_REQUEST.value(), String.format(METHOD_IS_NOT_ALLOWED.getValue(), httpMethod)));
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(new MetaResponse(FALSE, BAD_REQUEST.value(), ex.getLocalizedMessage().toUpperCase()));
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(new MetaResponse(FALSE, BAD_REQUEST.value(), ex.getLocalizedMessage().toUpperCase()));
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(new MetaResponse(FALSE, BAD_REQUEST.value(), ex.getLocalizedMessage().toUpperCase()));
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(new MetaResponse(FALSE, BAD_REQUEST.value(), ex.getLocalizedMessage().toUpperCase()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<MetaResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(new MetaResponse(FALSE, NOT_FOUND.value(), ex.getLocalizedMessage().toUpperCase()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<MetaResponse> handleUsernameNotFoundException(UsernameNotFoundException ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new MetaResponse(FALSE, NOT_FOUND.value(), ex.getLocalizedMessage().toUpperCase()));
    }

    @ExceptionHandler(ResourceExistsException.class)
    public ResponseEntity<MetaResponse> handleResourceExistException(ResourceExistsException ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new MetaResponse(FALSE, CONFLICT.value(), ex.getLocalizedMessage().toUpperCase()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<MetaResponse> handleResourceModifyException(IllegalArgumentException ex, WebRequest request) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(new MetaResponse(FALSE, BAD_REQUEST.value(), ex.getLocalizedMessage().toUpperCase()));
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<MetaResponse> handleDisabledException(DisabledException ex, WebRequest request) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(new MetaResponse(FALSE, BAD_REQUEST.value(), ex.getLocalizedMessage().toUpperCase()));
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<MetaResponse> handleJwtException(JwtException ex, WebRequest request) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(new MetaResponse(FALSE, UNAUTHORIZED.value(), ex.getLocalizedMessage().toUpperCase()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<MetaResponse> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(new MetaResponse(FALSE, BAD_REQUEST.value(), ex.getLocalizedMessage().toUpperCase()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<MetaResponse> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        return ResponseEntity
                .status(FORBIDDEN)
                .body(new MetaResponse(FALSE, FORBIDDEN.value(), NOT_ENOUGH_PERMISSION.getValue()));
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<MetaResponse> handleLockedException(LockedException ex, WebRequest request) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(new MetaResponse(FALSE, UNAUTHORIZED.value(), NOT_ENOUGH_PERMISSION.getValue()));
    }

    private List<String> processAllErrors(List<ObjectError> allErrors) {
        return allErrors.stream().map(this::resolveLocalizedErrorMessage).collect(Collectors.toList());
    }

    private String resolveLocalizedErrorMessage(ObjectError objectError) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        String localizedErrorMessage = messageSource.getMessage(objectError, currentLocale);
        log.info(localizedErrorMessage);
        return localizedErrorMessage;
    }
}
