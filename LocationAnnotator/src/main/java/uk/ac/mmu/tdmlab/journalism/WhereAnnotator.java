package uk.ac.mmu.tdmlab.journalism;

import java.util.Collection;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import uk.ac.mmu.tdmlab.journalism.Where;
import uk.ac.mmu.tdmlab.uima.Location;

public class WhereAnnotator extends JCasAnnotator_ImplBase
{

  @Override
  public void process(JCas jcas) throws AnalysisEngineProcessException
  {
    Collection<Location> locations = JCasUtil.select(jcas, Location.class);
    
    for(Location location : locations)
    {      
      Where where = new Where(jcas);
      where.setBegin(location.getBegin());
      where.setEnd(location.getEnd());
      jcas.addFsToIndexes(where);
    }
  }

}
