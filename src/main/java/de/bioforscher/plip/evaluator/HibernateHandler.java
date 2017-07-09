package de.bioforscher.plip.evaluator;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.util.SerializationHelper;

import java.util.ArrayList;
import java.util.List;


public class HibernateHandler {

    private Configuration con;
    private SessionFactory sf;
    private Session session;

    public void openSession(){

        con = new Configuration().configure().addAnnotatedClass(Protein.class);

        sf = con.buildSessionFactory();

        session = sf.openSession();
    }


    public void storeProtein(Protein protein){

        Interaction[] interactionsArray = protein.getInteractions();
        byte[] interactions = SerializationHelper.serialize(interactionsArray);

        Protein prot = protein;
        prot.setInteractionsByte(interactions);

        Transaction ta = session.beginTransaction();

        session.saveOrUpdate(prot);

        ta.commit();
    }


    public Protein fetchProtein(String PDBid){

        Transaction ta = session.beginTransaction();

        Protein fetchedProtein = session.get(Protein.class, PDBid);

        Interaction[] fetchedInteractions = (Interaction[]) SerializationHelper.deserialize(fetchedProtein.getInteractionsByte());

        fetchedProtein.setInteractions(fetchedInteractions);

        ta.commit();

        return fetchedProtein;
    }

    public List<Protein> fetchAllProteins(){

        Transaction ta = session.beginTransaction();

        List<Protein> fetchedProteinList = session.createCriteria(Protein.class).list();

        ta.commit();

        int size = fetchedProteinList.size();

        List<Protein> ProteinList = new ArrayList<Protein>();

        for (int i = 0; i < size; i++) {
            Protein currentProt = fetchedProteinList.get(i);
            Interaction[] interactions = (Interaction[]) SerializationHelper.deserialize(currentProt.getInteractionsByte());
            Protein newProtein = new Protein(currentProt.getDoi(),currentProt.getPDBid(),currentProt.getChain(),interactions);
            ProteinList.add(newProtein);
        }

        return ProteinList;
    }

    public void closeSession(){
        session.close();
        sf.close();
    }
}
