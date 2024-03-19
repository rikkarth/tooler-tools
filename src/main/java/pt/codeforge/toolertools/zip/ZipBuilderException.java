package pt.codeforge.toolertools.zip;

class ZipBuilderException extends RuntimeException {

    public ZipBuilderException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZipBuilderException(String message) {
        super(message);
    }

    public ZipBuilderException(Throwable cause) {
        super(cause);
    }

    public ZipBuilderException() {

    }
}
