package udesc.br.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import udesc.br.controller.ConsultaRepositorioListener;
import udesc.br.jpa.JPAConnector;
import udesc.br.model.Consulta;
import udesc.br.model.Paciente;
import udesc.br.repository.ConsultaRepositorio;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class ConsultaDAO implements ConsultaRepositorio {
    private EntityManager em;
    private ConsultaRepositorioListener listener;

    public void setListener(ConsultaRepositorioListener listener) {
        this.listener = listener;
    }

    @Override
    public void salvarConsulta(Consulta consulta) {
        em = JPAConnector.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(consulta);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
            listener.consultaAlterada(consulta);
        }
    }

    @Override
    public List<Consulta> buscarTodasConsultas() {
        em = JPAConnector.getEntityManager();
        try {
            TypedQuery<Consulta> query = em.createQuery("FROM Consulta ", Consulta.class);
            return query.getResultList();
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Consulta> buscarConsultasData(int mes, int ano) {
        em = JPAConnector.getEntityManager();

        YearMonth anoMes = YearMonth.of(ano, mes);
        LocalDate dataInicio = LocalDate.of(ano, mes, 1);
        LocalDate dataFim = LocalDate.of(ano, mes, anoMes.lengthOfMonth());

        try {
            TypedQuery<Consulta> query = em.createQuery("FROM Consulta WHERE data BETWEEN :inicio AND :fim", Consulta.class);
            query.setParameter("inicio", dataInicio);
            query.setParameter("fim", dataFim);
            return query.getResultList();
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Consulta buscarConsultaPorId(long id) {
        em = JPAConnector.getEntityManager();
        return em.find(Consulta.class, id);
    }

    @Override
    public void apagarConsulta(Consulta consulta) {
        em = JPAConnector.getEntityManager();
        try {
            em.getTransaction().begin();
            Consulta c = em.find(Consulta.class, consulta.getId());
            em.remove(c);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
            listener.consultaAlterada(consulta);
        }
    }
}












