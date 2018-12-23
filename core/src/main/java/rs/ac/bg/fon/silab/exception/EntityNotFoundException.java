package rs.ac.bg.fon.silab.exception;

public class EntityNotFoundException extends RuntimeException {

    private String errorMessageKey;

    public EntityNotFoundException(String errorMessageKey) {
        this.errorMessageKey = errorMessageKey;
    }

    public String getErrorMessageKey() {
        return errorMessageKey;
    }

    public void setErrorMessageKey(String errorMessageKey) {
        this.errorMessageKey = errorMessageKey;
    }

}
