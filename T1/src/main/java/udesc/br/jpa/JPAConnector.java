package udesc.br.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAConnector {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("clinica-pu");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
