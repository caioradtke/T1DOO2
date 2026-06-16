package udesc.br.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import udesc.br.jpa.JPAConnector;
import udesc.br.model.MovimentacaoFinanceira;
import udesc.br.repository.MovimentacaoFinanceiraRepositorio;

public class MovimentacaoFinanceiraDAO implements MovimentacaoFinanceiraRepositorio {

@Override
public void salvarMovimentacaoFinanceira(MovimentacaoFinanceira mov) {
    EntityManager em = JPAConnector.getEntityManager();

    try {
        em.getTransaction().begin();
        em.merge(mov);
        em.getTransaction().commit();

    } catch (Exception e) {
        em.getTransaction().rollback();
        throw e;

    } finally {
        em.close();
    }
}

@Override
public List<MovimentacaoFinanceira> buscarTodasMovimentacaoFinanceiras() {

    EntityManager em = JPAConnector.getEntityManager();

    try {
        TypedQuery<MovimentacaoFinanceira> query =
                em.createQuery(
                        "FROM MovimentacaoFinanceira",
                        MovimentacaoFinanceira.class);

        return query.getResultList();

    } finally {
        em.close();
    }
}

@Override
public void apagar(MovimentacaoFinanceira mov) {

    EntityManager em = JPAConnector.getEntityManager();

    try {
        em.getTransaction().begin();

        MovimentacaoFinanceira m =
                em.find(MovimentacaoFinanceira.class, mov.getId());

        if (m != null) {
            em.remove(m);
        }

        em.getTransaction().commit();

    } catch (Exception e) {
        em.getTransaction().rollback();
        throw e;

    } finally {
        em.close();
    }
}
}
