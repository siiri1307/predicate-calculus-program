package predmoodul.valemid;

import predmoodul.kvantorid.Kvantor;
import predmoodul.termid.IndiviidTerm;

import java.util.*;

/**
 * Created by siiri on 05/03/17.
 */
public class Konjuktsioon extends Valem {

    private List<Kvantor> kvantorid;
    private Valem valem;
    private boolean eitus;

    public Konjuktsioon(Valem valem){
        this.valem = valem;
    }

    public Konjuktsioon(List<Kvantor> kvantorid, Valem valem, boolean eitus){
        this.kvantorid = kvantorid;
        this.valem = valem;
        this.eitus = eitus;
    }


    @Override
    public List<Object> getChildren() {
        return Arrays.asList((Object) kvantorid,valem, eitus);
    }

    @Override
    public boolean vaartusta(Map<Character, Double> vaartustus) {
        return vaartustaAbi(vaartustus, new ArrayList<>(kvantorid));

    }

    @Override
    public Set<IndiviidTerm> getIndiviidTermid() {
        return valem.getIndiviidTermid();
    }

    @Override
    public Set<Character> getVabadMuutujad() {

        Set<Character> kvantoritegaSeotud = new HashSet<>();
        Set<Character> koikMuutujad = new HashSet<>();

        for(Kvantor kv : kvantorid){
            kvantoritegaSeotud.add(kv.getIndiviidMuutuja());
        }

        Set<IndiviidTerm> koikIndiviidMuutujad = getIndiviidTermid();

        for(IndiviidTerm indterm : koikIndiviidMuutujad){
            koikMuutujad.add(indterm.getTahis());
        }

        koikMuutujad.removeAll(kvantoritegaSeotud);
        System.out.println("Kvantoriga seotud muutujad on: " + kvantoritegaSeotud);

        System.out.println("Vabad muutujad on: " + koikMuutujad);

        return koikMuutujad; //TODO: kahe hulga vahe ei pruugi töötada kui muutuja võib ühes valemis esineda nii vaba kui seotuna!!
    }

    //if(eitus){
          //  return !valem.vaartusta(vaartustus);
        //}
        //return valem.vaartusta(vaartustus);


    boolean vaartustaAbi(Map<Character, Double> vaartustus, List<Kvantor> kvantorid){
        if (kvantorid.isEmpty()) {
            return valem.vaartusta(vaartustus);
        }

        List<Kvantor> koopiakvantoritest = new ArrayList<>(kvantorid);
        Kvantor kvantor = koopiakvantoritest.remove(0);
        for(double j= 0; j < 1000; j++){
            vaartustus.put(kvantor.getIndiviidMuutuja(), j);
            boolean toeVaartus = vaartustaAbi(vaartustus, koopiakvantoritest);
            if(kvantor.lõpetamiseTingimus() == toeVaartus){
                System.out.println("Kontramudel: " + vaartustus);
                return kvantor.lõpetamiseTingimus();
            }
        }

        return !kvantor.lõpetamiseTingimus();

    }


}
