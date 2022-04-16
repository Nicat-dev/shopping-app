package az.nicat.shoppingapp.exception;

public class ResourceExistsException extends RuntimeException {
    public ResourceExistsException(String resource, String  field, Object value) {
        super(String.format(" %s with %s => [ %s ] already exists",resource,field,value));
    }
}
