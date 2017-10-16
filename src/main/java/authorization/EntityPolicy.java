package authorization;

import org.springframework.security.access.AccessDeniedException;

public abstract class EntityPolicy<T> {
    abstract void authorizeList() throws AccessDeniedException;

    abstract void authorizeRead(T entityInstance) throws AccessDeniedException;

    abstract void authorizeUpdate(T entityInstance) throws AccessDeniedException;

    abstract void authorizeCreate(Class<T> entityClass) throws AccessDeniedException;

    abstract void authorizeDelete(T entityInstance) throws AccessDeniedException;
}
