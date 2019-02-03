package rs.ac.bg.fon.silab.exception;

public class WrongQueryParamsException extends RuntimeException {

    private String errorMessageKey;

    public WrongQueryParamsException(String errorMessageKey) {
        this.errorMessageKey = errorMessageKey;
    }

    public String getErrorMessageKey() {
        return errorMessageKey;
    }

    public void setErrorMessageKey(String errorMessageKey) {
        this.errorMessageKey = errorMessageKey;
    }

}
