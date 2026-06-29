package udesc.br.dao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import udesc.br.jpa.JPAConnector;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import udesc.br.model.Medicamento;
import udesc.br.repository.MedicamentoRepositorio;

public class MedicamentoDAO implements MedicamentoRepositorio {

@Override
public void salvarMedicamento(Medicamento medicamento) {
    EntityManager em = JPAConnector.getEntityManager();
    try {
        em.getTransaction().begin(); // comeca a transacao
        em.merge(medicamento); // JPA salva o medicamento no postgres
        em.getTransaction().commit(); // aplica/confirma a transacao
    } catch (Exception e) {
        em.getTransaction().rollback(); // desfaz a transacao
        throw e;
    } finally {
        em.close(); // desconecta o entity manager do postgres
    }
}


    @Override
    public Set<Medicamento> buscarTodosMedicamentos() {
        EntityManager em = JPAConnector.getEntityManager();
        try {
            TypedQuery<Medicamento> query = em.createQuery("FROM Medicamento", Medicamento.class);
            return new HashSet<>(query.getResultList());
        } finally {
            em.close();
        }
    }

    @Override
    public void apagar(Medicamento medicamento) {
        EntityManager em = JPAConnector.getEntityManager();
        try {
            em.getTransaction().begin();
            Medicamento m = em.find(Medicamento.class, medicamento.getId());
            em.remove(m);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    
}
