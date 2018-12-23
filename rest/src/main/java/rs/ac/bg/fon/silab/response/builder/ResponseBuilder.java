package rs.ac.bg.fon.silab.response.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import rs.ac.bg.fon.silab.response.Response;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ResponseBuilder {

    @Autowired
    private MessageSource messageSource;

    private Response response;
    private HttpStatus status;

    public ResponseBuilder createResponse() {
        response = new Response();
        return this;
    }

    public ResponseBuilder withStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public ResponseBuilder withMessageKey(String messageKey) {
        String message = messageSource.getMessage(messageKey, null, Locale.getDefault());
        response.setMessage(message);
        return this;
    }

    public ResponseBuilder withMessage(String message) {
        response.setMessage(message);
        return this;
    }

    public ResponseBuilder withErrors(Map<String, List<String>> errors) {
        response.setErrors(errors);
        return this;
    }

    public ResponseEntity<Object> build() {
        return new ResponseEntity<>(response, status);
    }

}
