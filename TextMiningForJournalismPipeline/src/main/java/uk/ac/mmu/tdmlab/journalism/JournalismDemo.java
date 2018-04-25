package uk.ac.mmu.tdmlab.journalism;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;

//import de.tudarmstadt.ukp.dkpro.core.io.pdf.PdfReader;
import uk.ac.mmu.tdmlab.uima.AnnotationSummariser;
import uk.ac.mmu.tdmlab.uima.PDFReader;
import uk.ac.mmu.tdmlab.journalism.StanfordNLPTagger;
import uk.ac.mmu.tdmlab.journalism.WhereAnnotator;
import uk.ac.mmu.tdmlab.journalism.WhenAnnotator;
import uk.ac.mmu.tdmlab.journalism.WhoAnnotator;

public class JournalismDemo
{
  public static void main(String[] args) throws Exception
  {
    // An ordered list of components that forms the pipeline
    List<AnalysisEngine> engines = new ArrayList<AnalysisEngine>();

    String pdfDir = "src/main/resources/airPollution20/";

    // PDF Reader
    CollectionReader pdfReader = CollectionReaderFactory
        .createReader(PDFReader.class, PDFReader.PARAM_DIRECTORY, pdfDir);

    // TODO - refactor StanfordNLP TS to be in uima space
    // Tagger
    engines.add(AnalysisEngineFactory.createEngine(StanfordNLPTagger.class));

    // Where annotations
    engines.add(AnalysisEngineFactory.createEngine(WhereAnnotator.class));

    // Who annotations
    engines.add(AnalysisEngineFactory.createEngine(WhoAnnotator.class));

    // When annotations
    engines.add(AnalysisEngineFactory.createEngine(WhenAnnotator.class));

    // annotation summary
    engines.add(AnalysisEngineFactory.createEngine(AnnotationSummariser.class,
        AnnotationSummariser.PARAM_TYPE_LIST,
        new String[] { "uk.ac.mmu.tdmlab.journalism.Who",
            "uk.ac.mmu.tdmlab.journalism.Where",
            "uk.ac.mmu.tdmlab.journalism.When" },
        AnnotationSummariser.PARAM_TOP_N, 0,
        AnnotationSummariser.PARAM_FILE_NAME, "src/main/resources/output.txt"));

    // run the pipeline
    SimplePipeline.runPipeline(pdfReader,
        engines.toArray(new AnalysisEngine[engines.size()]));
  }
}
