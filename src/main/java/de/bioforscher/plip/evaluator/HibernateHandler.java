package de.bioforscher.plip.evaluator;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.util.SerializationHelper;


public class HibernateHandler {

    public void storeProtein(Protein protein){

        Interaction[] interactionsArray = protein.getInteractions();
        byte[] interactions = SerializationHelper.serialize(interactionsArray);

        Protein prot = protein;
        prot.setInteractionsByte(interactions);

        Configuration con = new Configuration().configure().addAnnotatedClass(Protein.class);

        SessionFactory sf = con.buildSessionFactory();

        Session session = sf.openSession();

        Transaction ta = session.beginTransaction();

        session.save(prot);

        ta.commit();
        session.close();
        sf.close();

    }


    public Protein fetchProtein(String PDBid){

        Configuration con = new Configuration().configure().addAnnotatedClass(Protein.class);

        SessionFactory sf = con.buildSessionFactory();

        Session session = sf.openSession();

        Transaction ta = session.beginTransaction();

        Protein fetchedProtein = session.get(Protein.class, PDBid);

        Interaction[] fetchedInteractions = (Interaction[]) SerializationHelper.deserialize(fetchedProtein.getInteractionsByte());

        fetchedProtein.setInteractions(fetchedInteractions);

        ta.commit();
        session.close();
        sf.close();

        return fetchedProtein;
    }

}
