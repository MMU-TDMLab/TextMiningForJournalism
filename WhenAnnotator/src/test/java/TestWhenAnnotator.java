import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.jcas.JCas;

import uk.ac.mmu.tdmlab.uima.Time;
import uk.ac.mmu.tdmlab.journalism.WhenAnnotator;
import uk.ac.mmu.tdmlab.uima.LightweightCVD;

public class TestWhenAnnotator
{

  public static void main(String[] args) throws Exception
  {
    JCas jCas = JCasFactory.createJCas();

    jCas.setDocumentText(
        "Labour leader Jeremy Corbyn has defended his reluctance to blame Russia categorically for the Salisbury nerve agent attack, insisting the government must avoid \"hasty judgements\".");

     Time time = new Time(jCas);
     time.setBegin(0);
     time.setEnd(6);
     
     jCas.addFsToIndexes(time);
          
    AnalysisEngine analysisEngine =
        AnalysisEngineFactory.createEngine(WhenAnnotator.class);

    analysisEngine.process(jCas);

    LightweightCVD.launchCVD(jCas.getCas());

  }

}
