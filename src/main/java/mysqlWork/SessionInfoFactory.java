package mysqlWork;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class SessionInfoFactory {
    private EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("botoop");

    public SessionInfoService getSessionInfoService(){
        return new SessionInfoService(this.entityManagerFactory.createEntityManager());
    }
}
