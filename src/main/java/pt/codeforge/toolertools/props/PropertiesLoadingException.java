package pt.codeforge.toolertools.props;

class PropertiesLoadingException extends RuntimeException {

    public PropertiesLoadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public PropertiesLoadingException(String message) {
        super(message);
    }

    public PropertiesLoadingException(Throwable cause) {
        super(cause);
    }

    public PropertiesLoadingException() {
    }
}
