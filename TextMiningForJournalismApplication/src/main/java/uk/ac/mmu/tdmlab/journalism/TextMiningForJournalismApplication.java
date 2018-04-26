package uk.ac.mmu.tdmlab.journalism;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.LanguageCapability;
import org.apache.uima.fit.descriptor.MimeTypeCapability;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import eu.openminted.share.annotations.api.Component;
import eu.openminted.share.annotations.api.ResourceInput;
import eu.openminted.share.annotations.api.ResourceOutput;
import eu.openminted.share.annotations.api.constants.DataFormatType;
import eu.openminted.share.annotations.api.constants.OperationType;
import eu.openminted.share.annotations.api.constants.ProcessingResourceType;

import eu.openminted.share.annotations.api.DataFormat;
import eu.openminted.share.annotations.api.Language;
import eu.openminted.share.annotations.api.constants.AnnotationType;
import eu.openminted.share.annotations.api.constants.CharacterEncoding;


@Component(OperationType.ANNOTATOR)
@ResourceInput(
        type = ProcessingResourceType.DOCUMENT,
        encoding = CharacterEncoding.UTF_8,
        annotationLevel = AnnotationType.LEMMA,
        language = @Language(languageId="en"),
        dataFormat = @DataFormat(dataFormat = DataFormatType.BINARY_CAS))
@ResourceOutput(
        type = ProcessingResourceType.DOCUMENT,
        encoding = CharacterEncoding.UTF_8,
        annotationLevel = AnnotationType.LEMMA,
        language = @Language(languageId="en"),
        dataFormat = @DataFormat(dataFormat = DataFormatType.BINARY_CAS))
@LanguageCapability("en")
@TypeCapability(
        inputs = {}, 
        outputs = {"uk.ac.mmu.tdmlab.uima.Location",
            "uk.ac.mmu.tdmlab.uima.Organisation",
            "uk.ac.mmu.tdmlab.uima.Person",
            "uk.ac.mmu.tdmlab.uima.Time",
            "uk.ac.mmu.tdmlab.journalism.What",
            "uk.ac.mmu.tdmlab.journalism.Where",
            "uk.ac.mmu.tdmlab.journalism.When",
            "uk.ac.mmu.tdmlab.journalism.Who"
            })

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
