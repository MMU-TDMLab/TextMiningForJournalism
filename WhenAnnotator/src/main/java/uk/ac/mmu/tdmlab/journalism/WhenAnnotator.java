package uk.ac.mmu.tdmlab.journalism;

import java.util.Collection;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

public class WhenAnnotator extends JCasAnnotator_ImplBase
{

  @Override
  public void process(JCas jcas) throws AnalysisEngineProcessException
  {
    Collection<Time> times = JCasUtil.select(jcas, Time.class);
    
    for(Time time : times)
    {      
      When when = new When(jcas);
      when.setBegin(time.getBegin());
      when.setEnd(time.getEnd());
      jcas.addFsToIndexes(when);
    }
  }

}
