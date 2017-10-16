package authorization;

import com.juniorisep.catnix.common.dto.study.StudyDTO;
import com.juniorisep.catnix.gateway.security.OidcUserDetails;
import com.juniorisep.catnix.gateway.security.authorization.explicitauthorizationcheck.AuthorizationStatusHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {
    private Logger logger = LoggerFactory.getLogger(AuthorizationService.class);

    @Autowired
    private AuthorizationStatusHolder authorizationStatusHolder;

    public void authorizePublic() {
        authorizationStatusHolder.setExplicitelyAuthorized();
    }

    public void authorizeListStudies() {
        authorizationStatusHolder.setExplicitelyAuthorized();

        // TODO
    }

    public void authorizeReadStudy(StudyDTO studyDTO) {
        OidcUserDetails userDetails = (OidcUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.info("Authorizing user with sub " + userDetails.getSub() + " to access study with id " + studyDTO.getId());

        authorizationStatusHolder.setExplicitelyAuthorized();

        // TODO if the user is the study follower, or if she is president / treasurer ... etc...
    }
}
