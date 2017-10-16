package authorization.explicitauthorizationcheck;

class RequestNotExplicitelyAuthorizedException extends RuntimeException {
    private static String newline = System.getProperty("line.separator");

    public RequestNotExplicitelyAuthorizedException() {
        super("SECURITY WARNING " + newline +
                "This request was not explicitely authorized. This means it was handled by the server and the potential changes" + newline +
                "it was trying to perform HAVE BEEN PERFORMED." + newline +
                "However, there is a potential security threat, since no authorization checks were performed during the handling of" + newline +
                "this request. Perform the appropriate authorization checks using AuthorizationService." + newline +
                "* If this endpoint should be allowed to anyone, you MUST explicitly call AuthorizationService.authorizePublic()." + newline +
                "* If you have called an authorization method, make sure that authorization method calls " + newline +
                "AuthorizationStatusHolder.setExplicitelyAuthorized when the authorization check is positive" + newline + newline +
                "Full stack trace :");
    }
}
