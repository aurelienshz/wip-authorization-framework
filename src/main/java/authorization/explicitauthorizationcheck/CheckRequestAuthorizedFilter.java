package authorization.explicitauthorizationcheck;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.juniorisep.catnix.common.exception.InvalidStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CheckRequestAuthorizedFilter extends OncePerRequestFilter {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AuthorizationStatusHolder authorizationStatusHolder;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (this.authorizationStatusHolder == null) throw new InvalidStateException("CheckRequestAuthorizedFilter received no autowired AuthorizationStatusHolder");

        // We have to cache the response, because once Spring has started writing in it, we cannot replace its content easily anymore
        // So we wrap the actual response in an object whose OutputWriter is not the actual HTTP ouputWriter, and, once the request has been
        // handled, we perform our checks on whether it was explicitely authorized.

        // Adapted from https://stackoverflow.com/questions/25020331/spring-mvc-how-to-modify-json-response-sent-from-controller/25155407#25155407
        // TODO use org.springframework.web.util.ContentCachingResponseWrapper instead ? (https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/util/ContentCachingResponseWrapper.html)

        ResponseWrapper responseWrapper = new ResponseWrapper(response);

        filterChain.doFilter(request, responseWrapper);

        try {
            if (authorizationStatusHolder.hasBeenAuthorized()) {
                response.getOutputStream().write(responseWrapper.getDataStream());
            } else {
                throw new RequestNotExplicitelyAuthorizedException();
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(401);
            response.getOutputStream().write(convertObjectToJson(e).getBytes());
        }

        logger.debug("CheckRequestAuthorizedFilter after doFilter");
    }

    public String convertObjectToJson(Exception exception) throws JsonProcessingException {
        if (exception == null) {
            return null;
        }

        // Clear the stack trace so as not to disclose too much information about this error to the client
        exception.setStackTrace(new StackTraceElement[0]);

        ObjectMapper mapper = new ObjectMapper();

        SimpleBeanPropertyFilter stackTraceFilter = SimpleBeanPropertyFilter.serializeAllExcept("stackTrace");
        FilterProvider filters = new SimpleFilterProvider().addFilter("stackTraceFilter", stackTraceFilter);

        return mapper.writer(filters).writeValueAsString(exception);
    }
}