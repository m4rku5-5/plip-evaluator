package de.bioforscher.plip.evaluator;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.internal.util.SerializationHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * handling of storage of proteins in a database
 */


public class HibernateHandler {

    private Configuration con;
    private SessionFactory sf;
    private Session session;

    public void openSession(){

        //loading configuration file
        con = new Configuration().configure().addAnnotatedClass(ProteinByte.class);

        sf = con.buildSessionFactory();

        session = sf.openSession();
    }

    public boolean containsProtein(String pdbid) {

        Transaction ta = session.beginTransaction();

        List<String> proteinList = session.createCriteria(ProteinByte.class).setProjection(Projections.property("PDBid")).list();

        boolean contains = proteinList.contains(pdbid);

        ta.commit();

        return contains;
    }

    public void storeProtein(Protein protein){

        //make new container
        PredictedContainer predictedContainer = protein.getPredictedContainer();
        byte[] predictedContainerByte = SerializationHelper.serialize(predictedContainer);

        ProteinByte proteinByte = new ProteinByte(protein.getDoi(), protein.getPDBid(), protein.getChain(), predictedContainerByte);

        Transaction ta = session.beginTransaction();

        session.saveOrUpdate(proteinByte);

        ta.commit();
    }


    public Protein fetchProtein(String PDBid){

        Transaction ta = session.beginTransaction();

       // Protein protein = (Protein) session.createSQLQuery("SELECT * FROM protein").list().get(0);

        ProteinByte fetchedProtein = session.get(ProteinByte.class, PDBid);

        System.out.println(fetchedProtein.getPredictedContainerByte());

        //PredictedContainer fetchedpredictedContainer = (PredictedContainer) SerializationHelper.deserialize();

        //fetchedProtein.setPredictedContainer(fetchedpredictedContainer);

        ta.commit();

        return null;
    }

    public List<Protein> fetchAllProteins(){

        Transaction ta = session.beginTransaction();

        List<Protein> fetchedProteinList = session.createCriteria(Protein.class).list();

        ta.commit();

        /*int size = fetchedProteinList.size();

        List<Protein> ProteinList = new ArrayList<Protein>();

        for (int i = 0; i < size; i++) {
            Protein currentProt = fetchedProteinList.get(i);
            PredictedContainer fetchedpredictedContainer = (PredictedContainer) SerializationHelper.deserialize(currentProt.getPredictedContainerByte());
            Protein newProtein = new Protein(currentProt.getDoi(),currentProt.getPDBid(),currentProt.getChain(),fetchedpredictedContainer);
            ProteinList.add(newProtein);
        }
*/
        return fetchedProteinList;
    }

    public void closeSession(){
        session.close();
        sf.close();
    }

}
