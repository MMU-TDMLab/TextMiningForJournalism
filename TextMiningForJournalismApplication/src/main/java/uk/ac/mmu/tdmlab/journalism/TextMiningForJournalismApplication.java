package uk.ac.mmu.tdmlab.journalism;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

public class TextMiningForJournalismApplication extends JCasAnnotator_ImplBase
{

  // An ordered list of components that forms the pipeline
  private List<AnalysisEngine> engines;
  
  @Override
  public void initialize(UimaContext context) throws ResourceInitializationException
  {
    engines = new ArrayList<AnalysisEngine>();
    
    // Tagger
    engines.add(AnalysisEngineFactory.createEngine(StanfordNLPTagger.class));

    // Where annotations
    engines.add(AnalysisEngineFactory.createEngine(WhereAnnotator.class));

    // Who annotations
    engines.add(AnalysisEngineFactory.createEngine(WhoAnnotator.class));

    // When annotations
    engines.add(AnalysisEngineFactory.createEngine(WhenAnnotator.class));

  }
  
  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException
  {
    // run the pipeline
    SimplePipeline.runPipeline(aJCas,
        engines.toArray(new AnalysisEngine[engines.size()]));
  }

}
