package udesc.br.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import udesc.br.jpa.JPAConnector;
import udesc.br.model.Agenda;
import udesc.br.model.Consulta;
import udesc.br.repository.AgendaRepositorio;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;

public class AgendaDAO implements AgendaRepositorio {
    private EntityManager em;

    @Override
    public void salvarRepositorio(Agenda agenda) {
        em = JPAConnector.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(agenda);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Agenda> buscarTodasAgendas() {
        em = JPAConnector.getEntityManager();
        try {
            TypedQuery<Agenda> query = em.createQuery("FROM Agenda ", Agenda.class);
            return query.getResultList();
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void apagarAgenda(Agenda agenda) {

    }

    @Override
    public List<Agenda> buscarAgendasData(int mes, int ano) {
        em = JPAConnector.getEntityManager();

        YearMonth anoMes = YearMonth.of(ano, mes);
        LocalDate dataInicio = LocalDate.of(ano, mes, 1);
        LocalDate dataFim = LocalDate.of(ano, mes, anoMes.getMonthValue());

        try {
            TypedQuery<Agenda> query = em.createQuery("FROM Consulta WHERE data BETWEEN :inicio AND :fim", Agenda.class);
            query.setParameter("inicio", dataInicio);
            query.setParameter("fim", dataFim);
            return query.getResultList();
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
    }
}
