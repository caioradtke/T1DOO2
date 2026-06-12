package udesc.br.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import udesc.br.jpa.JPAConnector;
import udesc.br.model.Paciente;
import udesc.br.repository.PacienteRepositorio;

import java.util.List;

public class PacienteDAO implements PacienteRepositorio {

    @Override
    public void salvarPaciente(Paciente paciente) {
        EntityManager em = JPAConnector.getEntityManager();
        try {
            em.getTransaction().begin(); // comeca a transacao
            em.merge(paciente); // JPA salva o paciente no postgres
            em.getTransaction().commit(); // aplica/confirma a transacao
        } catch (Exception e) {
            em.getTransaction().rollback(); // desfaz a transacao
            throw e;
        } finally {
            em.close(); // desconecta o entity manager do postgres
        }
    }

    @Override
    public List<Paciente> buscarTodosPacientes() {
        EntityManager em = JPAConnector.getEntityManager();
        try {
            TypedQuery<Paciente> query = em.createQuery("FROM Paciente", Paciente.class);
            return query.getResultList();
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void apagar(Paciente paciente) {
    }
}
