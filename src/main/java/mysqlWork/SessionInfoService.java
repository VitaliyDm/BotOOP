package mysqlWork;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class SessionInfoService {

    public EntityManager entityManager =
            Persistence.createEntityManagerFactory("botoop").createEntityManager();

    public SessionEntity get(long chatId){
        return entityManager.find(SessionEntity.class, chatId);
    }

    public void update(SessionEntity sessionInfo){
        entityManager.getTransaction().begin();
        entityManager.merge(sessionInfo);
        entityManager.getTransaction().commit();
    }

    public SessionEntity add(SessionEntity sessionInfo){
        entityManager.getTransaction().begin();
        SessionEntity sessionInfoFromDb = entityManager.merge(sessionInfo);
        entityManager.getTransaction().commit();
        return sessionInfoFromDb;
    }

    public void delete(long chatId){
        entityManager.getTransaction().begin();
        entityManager.remove(get(chatId));
        entityManager.getTransaction().commit();
    }
}
