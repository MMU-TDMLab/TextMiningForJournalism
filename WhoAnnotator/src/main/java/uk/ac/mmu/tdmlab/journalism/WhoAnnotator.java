package uk.ac.mmu.tdmlab.journalism;

import java.util.Collection;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import uk.ac.mmu.tdmlab.uima.Organisation;
import uk.ac.mmu.tdmlab.uima.Person;

public class WhoAnnotator extends JCasAnnotator_ImplBase
{

  @Override
  public void process(JCas jcas) throws AnalysisEngineProcessException
  {
    Collection<Person> people = JCasUtil.select(jcas, Person.class);
    
    for(Person person : people)
    {      
      Who who = new Who(jcas);
      who.setBegin(person.getBegin());
      who.setEnd(person.getEnd());
      jcas.addFsToIndexes(who);
    }
    
    Collection<Organisation> organisations = JCasUtil.select(jcas, Organisation.class);
    
    for(Organisation organisation : organisations)
    {      
      Who who = new Who(jcas);
      who.setBegin(organisation.getBegin());
      who.setEnd(organisation.getEnd());
      jcas.addFsToIndexes(who);
    }
  }

}
