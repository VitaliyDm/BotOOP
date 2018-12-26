package mysqlWork;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class SessionInfoService {

    public EntityManager entityManager =
            Persistence.createEntityManagerFactory("BotOOP").createEntityManager();

    public SessionInfo get(long chatId){
        return entityManager.find(SessionInfo.class, chatId);
    }

    public void update(SessionInfo sessionInfo){
        entityManager.getTransaction().begin();
        entityManager.merge(sessionInfo);
        entityManager.getTransaction().commit();
    }

    public SessionInfo add(SessionInfo sessionInfo){
        entityManager.getTransaction().begin();
        SessionInfo sessionInfoFromDb = entityManager.merge(sessionInfo);
        entityManager.getTransaction().commit();
        return sessionInfoFromDb;
    }

    public void delete(long chatId){
        entityManager.getTransaction().begin();
        entityManager.remove(get(chatId));
        entityManager.getTransaction().commit();
    }
}
