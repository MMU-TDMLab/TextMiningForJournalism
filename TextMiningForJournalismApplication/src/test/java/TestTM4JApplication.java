import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.jcas.JCas;

import uk.ac.mmu.tdmlab.journalism.TextMiningForJournalismApplication;
import uk.ac.mmu.tdmlab.uima.LightweightCVD;

public class TestTM4JApplication
{
  public static void main(String[] args) throws Exception
  {
    JCas jCas = JCasFactory.createJCas();
    jCas.setDocumentText(
        "The Duke and Duchess of Cambridge have left hospital after the arrival"
        + " of their third child, a boy. The couple's second son, who was born "
        + "at 11:01 BST, weighing 8lb 7oz, is fifth in line to the throne. "
        + "Prince George and Princess Charlotte had visited their brother at "
        + "the Lindo Wing of St Mary's Hospital, London.");

    AnalysisEngine textMining4JApp = AnalysisEngineFactory
        .createEngine(TextMiningForJournalismApplication.class);

    textMining4JApp.process(jCas);

    LightweightCVD.launchCVD(jCas.getCas());
  }
}
