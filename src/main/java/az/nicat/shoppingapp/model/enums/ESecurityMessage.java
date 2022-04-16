package az.nicat.shoppingapp.model.enums;

public enum ESecurityMessage {
    FORBIDDEN_MESSAGE("You need to login in to access this page"),
    ACCESS_DENIED_MESSAGE("You need to have permission to access this resource"),
    NOT_ENOUGH_PERMISSION("You do not have enough permission"),
    ACCOUNT_DISABLED("Your account has been disabled. If this is an error, please contact administration"),
    ACCOUNT_LOCKED("Your account has been locked.Please contact administration"),
    INCORRECT_CREDENTIALS("Username / password incorrect. Please try again"),
    METHOD_IS_NOT_ALLOWED("This request method is not allowed on this endpoint. Please send a '%s' request");
    private final String value;

    ESecurityMessage(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
