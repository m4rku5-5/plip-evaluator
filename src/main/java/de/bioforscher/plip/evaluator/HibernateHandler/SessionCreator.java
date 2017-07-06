package de.bioforscher.plip.evaluator.HibernateHandler;


import de.bioforscher.plip.evaluator.Interaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


public class SessionCreator {
    public static void main(String[] args) {


        Interaction fancyInt = new Interaction(5,3,3,4,"H-Bond");


        Configuration con = new Configuration().configure().addAnnotatedClass(Interaction.class);

        SessionFactory sf = con.buildSessionFactory();

        Session session = sf.openSession();

        Transaction ta = session.beginTransaction();

        session.save(fancyInt);

        ta.commit();


    }


}
