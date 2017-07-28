package predmoodul.termid;

import predmoodul.valemid.Muutuja;

import java.util.List;
import java.util.Map;

/**
 * Created by siiri on 18/03/17.
 */
public class JagamisTerm extends TehteTerm {

    /*public JagamisTerm(List<Term> termid) {
        super(termid);
    }*/

    //private Term vasakTerm;
    //private Term paremTerm;

    public JagamisTerm(Term vasakTerm, Term paremTerm){
        this.vasakTerm = vasakTerm;
        this.paremTerm = paremTerm;
    }

    public JagamisTerm(JagamisTerm jt){

        this.vasakTerm = jt.vasakTerm.koopia();
        this.paremTerm = jt.paremTerm.koopia();

        /*List<Term> termid = new ArrayList<>();

        for(Term t : jt.alamTermid){
            termid.add(t.koopia());
        }

        this.alamTermid = termid;*/
    }

    @Override
    public double vaartusta(Map<Muutuja, Double> vaartustus) {

        return vasakTerm.vaartusta(vaartustus) / paremTerm.vaartusta(vaartustus);

        //return teeTehe(vaartustus, alamTermid, (jagatav,jagaja) -> jagatav/jagaja );

    }

    //return  alamTermid.stream().flatMap(x -> x.getIndiviidTermid().stream()).collect(Collectors.toSet());

    public static Term binaarneJagatis(List<Term> termid) {

        Term vasakTerm = termid.get(0);

        for(int i = 1; i < termid.size(); i++){
            vasakTerm = new JagamisTerm(vasakTerm, termid.get(i));
        }

        return vasakTerm;
    }

    @Override
    public String dot() {

        String jagamisterm = "/Term(";

        jagamisterm += vasakTerm.dot() + ", ";

        jagamisterm += paremTerm.dot();

        jagamisterm += ")";

        /*for(int i = 0; i < alamTermid.size()-1; i++){
            jagamisterm += alamTermid.get(i).dot() + ",";
        }

        jagamisterm += alamTermid.get(alamTermid.size()-1) + ")";*/

        return jagamisterm;
    }

    @Override
    public Muutuja getTahis() {
        return null;
    }


    @Override
    public Term koopia() {
        return new JagamisTerm(this);
    }
}
