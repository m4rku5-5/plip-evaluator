package de.bioforscher.plip.evaluator;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.internal.util.SerializationHelper;
import org.hibernate.type.StandardBasicTypes;

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

        //make new container and serialize the predictedContainer
        PredictedContainer predictedContainer = protein.getPredictedContainer();
        byte[] predictedContainerByte = SerializationHelper.serialize(predictedContainer);

        ProteinByte proteinByte = new ProteinByte(protein.getDoi(), protein.getPDBid(), protein.getChain(), predictedContainerByte);

        Transaction ta = session.beginTransaction();

        session.saveOrUpdate(proteinByte);

        ta.commit();
    }

    public void mergeProtein(Protein protein){

        //same as storeProtein but other storing action
        PredictedContainer predictedContainer = protein.getPredictedContainer();
        byte[] predictedContainerByte = SerializationHelper.serialize(predictedContainer);

        ProteinByte proteinByte = new ProteinByte(protein.getDoi(), protein.getPDBid(), protein.getChain(), predictedContainerByte);

        Transaction ta = session.beginTransaction();

        session.merge(proteinByte);

        ta.commit();
    }


    public Protein fetchProtein(String PDBid){

        Transaction ta = session.beginTransaction();

        //getting and deserializing the protein/predictedContainer --> not the fine english way because it's a workaround!
        byte[] predictedContainerBytes = (byte[]) session.createSQLQuery("SELECT predictedContainerByte FROM `protein` WHERE `PDBid`=:PDBid ").addScalar("predictedContainerByte", StandardBasicTypes.BINARY).setString("PDBid", PDBid).uniqueResult();

        ProteinByte fetchedProtein = session.get(ProteinByte.class, PDBid);

        PredictedContainer fetchedpredictedContainer = (PredictedContainer) SerializationHelper.deserialize(predictedContainerBytes);

        fetchedProtein.setPredictedContainer(fetchedpredictedContainer);

        ta.commit();

        return fetchedProtein;
    }

    public List<Protein> fetchAllProteins(){

        //same as fetchProtein but before getting a list of all proteins and then fetching each individual and storing them in a list
        List<Protein> fetchedProteinList = new ArrayList<>();

        Transaction ta = session.beginTransaction();

        List<String> PDBidList = session.createSQLQuery("SELECT PDBid FROM `protein`").list();

        for (int i = 0; i < PDBidList.size(); i++) {

            byte[] predictedContainerBytes = (byte[]) session.createSQLQuery("SELECT predictedContainerByte FROM `protein` WHERE `PDBid`=:PDBid ").addScalar("predictedContainerByte", StandardBasicTypes.BINARY).setString("PDBid", PDBidList.get(i)).uniqueResult();

            ProteinByte fetchedProtein = session.get(ProteinByte.class, PDBidList.get(i));

            PredictedContainer fetchedpredictedContainer = (PredictedContainer) SerializationHelper.deserialize(predictedContainerBytes);

            fetchedProtein.setPredictedContainer(fetchedpredictedContainer);

            fetchedProteinList.add(fetchedProtein);

        }

        ta.commit();

        return fetchedProteinList;
    }

    public void closeSession(){
        session.close();
        sf.close();
    }

}
