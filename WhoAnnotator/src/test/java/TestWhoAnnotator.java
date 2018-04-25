import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.jcas.JCas;

import uk.ac.mmu.tdmlab.journalism.Organisation;
import uk.ac.mmu.tdmlab.journalism.Person;
import uk.ac.mmu.tdmlab.journalism.WhoAnnotator;
import uk.ac.mmu.tdmlab.uima.LightweightCVD;

public class TestWhoAnnotator
{

  public static void main(String[] args) throws Exception
  {
    JCas jCas = JCasFactory.createJCas();

    jCas.setDocumentText(
        "Labour leader Jeremy Corbyn has defended his reluctance to blame Russia categorically for the Salisbury nerve agent attack, insisting the government must avoid \"hasty judgements\".");

     Organisation organisation = new Organisation(jCas);
     organisation.setBegin(0);
     organisation.setEnd(6);
     jCas.addFsToIndexes(organisation);
     
     Person person = new Person(jCas);
     person.setBegin(7);
     person.setEnd(13);
     jCas.addFsToIndexes(person);

    AnalysisEngine analysisEngine =
        AnalysisEngineFactory.createEngine(WhoAnnotator.class);

    analysisEngine.process(jCas);

    LightweightCVD.launchCVD(jCas.getCas());

  }

}
