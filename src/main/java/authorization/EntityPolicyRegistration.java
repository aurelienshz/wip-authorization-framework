package authorization;

import org.springframework.security.access.AccessDeniedException;

import java.util.HashMap;
import java.util.Map;

public class EntityPolicyRegistration {
    private Map<Class<?>, EntityPolicy> registration = new HashMap<>();

    public EntityPolicyRegistration registerEntityPolicy(EntityPolicy entityPolicy, Class targetClass) {
        this.registration.put(targetClass, entityPolicy);
        return this;
    }

    private EntityPolicy findEntityPolicy(Class entityClass) {
        return this.registration.get(entityClass);
    }

    public void authorizeList(Class entityClass) throws AccessDeniedException {
        findEntityPolicy(entityClass).authorizeList();
    }

    public void authorizeRead(Object entityInstance) throws AccessDeniedException {
        Class<?> entityClass = entityInstance.getClass();
        EntityPolicy entityPolicy = findEntityPolicy(entityClass);
        entityPolicy.authorizeRead(entityInstance);
    }

    public void authorizeUpdate(Object entityInstance) throws AccessDeniedException {

    }

    public void authorizeCreate(Class entityClass) throws AccessDeniedException {

    }

    public void authorizeDelete(Object entityInstance) throws AccessDeniedException {

    }
}
