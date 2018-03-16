import java.io.File;
import java.lang.reflect.InvocationTargetException;

import java.net.URL;
import java.net.URLClassLoader;

import javax.swing.SwingUtilities;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASRuntimeException;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.tools.cvd.MainFrame;
import org.apache.uima.util.CasCopier;
import org.apache.uima.util.CasCreationUtils;

import uk.ac.mmu.tdmlab.journalism.StanfordNLPTagger;

public class TestStanfordNLPTagger
{
  public static void main(String[] args) throws Exception
  {

    ClassLoader classLoader = ClassLoader.getSystemClassLoader();

    URL[] urls = ((URLClassLoader) classLoader).getURLs();

    for (URL url : urls)
      System.out.println(url);

    JCas jCas = JCasFactory.createJCas();

    jCas.setDocumentText(
        "Labour leader Jeremy Corbyn has defended his reluctance to blame Russia categorically for the Salisbury nerve agent attack, insisting the government must avoid \"hasty judgements\".");

    AnalysisEngine analysisEngine =
        AnalysisEngineFactory.createEngine(StanfordNLPTagger.class);

    analysisEngine.process(jCas);

    // invoke(jCas.getCas(), null);

  }

  public static void invoke(CAS cas, final File casDir)
      throws InterruptedException, InvocationTargetException,
      CASRuntimeException, ResourceInitializationException
  {
    final CAS casCopy =
        CasCreationUtils.createCas(cas.getTypeSystem(), null, null, null);
    CasCopier.copyCas(cas, casCopy, true);
    cas.reset();
    SwingUtilities.invokeAndWait(new Runnable()
    {
      public void run()
      {
        MainFrame frame = new MainFrame(null);
        frame.pack();
        frame.setVisible(true);
        frame.setCas(casCopy);
        frame.handleSofas();
        frame.updateIndexTree(true);
        frame.setRunOnCasEnabled();
        frame.setEnableCasFileReadingAndWriting();
        frame.setTypeSystemViewerEnabled(true);
        if (casDir != null)
          frame.setXcasFileOpenDir(casDir);
      }
    });
  }
}
