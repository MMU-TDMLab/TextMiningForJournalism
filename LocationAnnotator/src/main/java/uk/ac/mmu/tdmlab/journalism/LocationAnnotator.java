package uk.ac.mmu.tdmlab.journalism;

import java.util.Collection;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import uk.ac.mmu.tdmlab.journalism.Location;
import uk.ac.mmu.tdmlab.journalism.Where;

public class LocationAnnotator extends JCasAnnotator_ImplBase
{

  @Override
  public void process(JCas jcas) throws AnalysisEngineProcessException
  {
    Collection<Location> allLocations = JCasUtil.select(jcas, Location.class);
    
    for(Location location : allLocations)
    {
      Where where = new Where();
      where.setBegin(location.getBegin());
      where.setEnd(location.getEnd());
      jcas.addFsToIndexes(where);
    }
  }

}
