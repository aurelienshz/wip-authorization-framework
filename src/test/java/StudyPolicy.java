import authorization.EntityPolicy;

public class StudyPolicy extends EntityPolicy<StudyDTO> {
    @Override
    void authorizeList() throws AccessDeniedException {

    }

    @Override
    void authorizeRead(StudyDTO entityInstance) throws AccessDeniedException {

    }

    @Override
    void authorizeUpdate(StudyDTO entityInstance) throws AccessDeniedException {

    }

    @Override
    void authorizeCreate(Class<StudyDTO> entityClass) throws AccessDeniedException {

    }

    @Override
    void authorizeDelete(StudyDTO entityInstance) throws AccessDeniedException {

    }
}
