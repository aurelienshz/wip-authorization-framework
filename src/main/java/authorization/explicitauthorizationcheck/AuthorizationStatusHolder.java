package authorization.explicitauthorizationcheck;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope // Refresh this class for every request, so that every request is marked as not authorized
public class AuthorizationStatusHolder {
    private boolean authorized = false;

    boolean hasBeenAuthorized() {
        return authorized;
    }

    public void setExplicitelyAuthorized() {
        this.authorized = true;
    }
}
