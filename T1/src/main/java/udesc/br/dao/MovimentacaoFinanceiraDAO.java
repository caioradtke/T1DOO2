package udesc.br.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

import udesc.br.jpa.JPAConnector;
import udesc.br.model.Despesa;
import udesc.br.model.Entrada;
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
    public double buscarTotalEntradasMes() {
        EntityManager em = JPAConnector.getEntityManager();

        try {
            TypedQuery<Double> query = em.createQuery(
                    "SELECT COALESCE(SUM(m.valor), 0) " +
                            "FROM MovimentacaoFinanceira m " +
                            "WHERE TYPE(m) = Entrada " +
                            "AND m.data >= :dataLimite",
                    Double.class
            );

            query.setParameter(
                    "dataLimite",
                    LocalDate.now().minusDays(30));

            return query.getSingleResult();

        } finally {
            em.close();
        }
    }

    public double buscarTotalDespesasMes() {
        EntityManager em = JPAConnector.getEntityManager();

        try {
            TypedQuery<Double> query = em.createQuery(
                    "SELECT COALESCE(SUM(m.valor), 0) " +
                            "FROM MovimentacaoFinanceira m " +
                            "WHERE TYPE(m) = Despesa " +
                            "AND m.data >= :dataLimite",
                    Double.class
            );

            query.setParameter(
                    "dataLimite",
                    LocalDate.now().minusDays(30));

            return query.getSingleResult();

        } finally {
            em.close();
        }
    }

    public double buscarTotalSaldoMes() {
           return buscarTotalEntradasMes() -  buscarTotalDespesasMes();
    }

    @Override
    public List<Despesa> buscarTodasDespesas() {
        
        EntityManager em = JPAConnector.getEntityManager();

        try {
            TypedQuery<Despesa> query =
                    em.createQuery(
                            "FROM Despesa",
                            Despesa.class);

            return query.getResultList();

        } finally {
            em.close();
        }
    }

    @Override
    public List<Entrada> buscarTodasEntradas() {
        
        EntityManager em = JPAConnector.getEntityManager();

        try {
            TypedQuery<Entrada> query =
                    em.createQuery(
                            "FROM Entrada",
                            Entrada.class);

            return query.getResultList();

        } finally {
            em.close();
        }
    }
}
