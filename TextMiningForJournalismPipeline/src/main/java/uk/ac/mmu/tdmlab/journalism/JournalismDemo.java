package uk.ac.mmu.tdmlab.journalism;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

public class JournalismDemo
{
  public static void main(String[] args) throws Exception
  {
    // Dummy input
    JCas jCas = JCasFactory.createJCas();
    jCas.setDocumentText("Labour leader Jeremy Corbyn has defended his "
        + "reluctance to blame Russia categorically for the Salisbury nerve "
        + "agent attack, insisting the government must avoid \"hasty "
        + "judgements\".");
    
    // An ordered list of components that forms the pipeline
    List<AnalysisEngine> engines =
        new ArrayList<AnalysisEngine>();

    // Tagger
    engines.add(AnalysisEngineFactory.createEngine(StanfordNLPTagger.class));

    // Where annotations
    engines.add(AnalysisEngineFactory.createEngine(LocationAnnotator.class));
    
    //run the pipeline
    SimplePipeline.runPipeline(jCas,
        engines.toArray(new AnalysisEngine[engines.size()]));

    // print out all NE annotations for debug
    for (Annotation annotation : jCas.getAnnotationIndex())
    {
      System.out.println(
          annotation.getType().toString() + ": " + annotation.getCoveredText());
    } // for

  }
}
