package udesc.br.app;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import udesc.br.vision.MenuInicialVisao;

public class Sistema {
     public static void main(String[] args){

         MenuInicialVisao menuInicialVisao = new MenuInicialVisao();
         menuInicialVisao.setVisible(true);
    }
}
