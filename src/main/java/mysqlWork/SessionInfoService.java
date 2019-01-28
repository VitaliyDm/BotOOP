package mysqlWork;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SessionInfoService {

    public SessionInfoService(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public EntityManager entityManager;

    public SessionEntity get(long chatId){
        return entityManager.find(SessionEntity.class, chatId);
    }

    public void saveAndClose(){
        entityManager.flush();
        //entityManager.close();
    }

    public void update(SessionEntity sessionInfo){
        entityManager.merge(sessionInfo);
    }

    public SessionEntity add(SessionEntity sessionInfo){
        SessionEntity sessionInfoFromDb = entityManager.merge(sessionInfo);
        return sessionInfoFromDb;
    }

    public void delete(long chatId){
        entityManager.remove(get(chatId));
    }
}
