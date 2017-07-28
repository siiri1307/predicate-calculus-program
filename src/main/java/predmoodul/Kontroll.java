package predmoodul;

import predmoodul.erindid.ErinevIndiviidideArv;
import predmoodul.kvantorid.Iga;
import predmoodul.valemid.Ekvivalents;
import predmoodul.valemid.Muutuja;
import predmoodul.valemid.Valem;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by siiri on 27/03/17.
 */
public class Kontroll {

    private Valem pakkumine;
    private Valem vastus;
    private Valem valem;
    private Map<Muutuja, Double> vaartustused;

    public Kontroll(Valem pakkumine, Valem vastus) {
        this.pakkumine = pakkumine;
        this.vastus = vastus;
        this.vaartustused = new HashMap<>();
    }

    public Kontroll(Valem valem){

        this.valem = valem;
        this.vaartustused = new HashMap<>();
    }

    public boolean eiOleSamavaarne() throws ErinevIndiviidideArv {
        int indiviidideArvPakkumine = pakkumine.getVabadMuutujad().size();
        //System.out.println("Pakkumine: " + pakkumine.getVabadMuutujad());
        int indiviidideArvVastus = vastus.getVabadMuutujad().size();
        //System.out.println("Vastus: " + vastus.getVabadMuutujad());
        if(indiviidideArvPakkumine != indiviidideArvVastus){
            throw new ErinevIndiviidideArv(indiviidideArvPakkumine, indiviidideArvVastus);
        }
        Valem ekvivalentsiValem = moodustaEkvivalentsiValem(pakkumine, vastus);
        Valem valemVabadeMuutujateta = seoVabadMuutujad(ekvivalentsiValem);
        //System.out.println("Valem vabade muutujateta: " + valemVabadeMuutujateta.getVabadMuutujad());
        return !valemVabadeMuutujateta.vaartusta(vaartustused);
    }

    public boolean eiOleSamavaarneIlmaErindita() throws ErinevIndiviidideArv { //see meetod on mõeldud väärtuste väljarvutamise testimiseks - see ei võta arvesse oodatavat predikaatide arvu

        Valem ekvivalentsiValem = moodustaEkvivalentsiValem(pakkumine, vastus);
        Valem valemVabadeMuutujateta = seoVabadMuutujad(ekvivalentsiValem);

        return !valemVabadeMuutujateta.vaartusta(vaartustused);
    }

    public boolean eiOleSamavaarne2(){
        Valem valemVabadeMuutujateta = seoVabadMuutujad(valem);
        return !valemVabadeMuutujateta.vaartusta(vaartustused);
    }

    public Map<Character, Integer> tagastaKontramudel(){

        Map<Character, Integer> kontramudel = new HashMap<>();

        Set<Muutuja> vabadMuutujad = pakkumine.getVabadMuutujad();

        //System.out.println("Vabu muutujaid on: " + vabadMuutujad.size());

        for(Muutuja ch : vabadMuutujad){

            if(vaartustused.containsKey(ch)){
                kontramudel.put(ch.getTahis(), vaartustused.get(ch).intValue()); //isegi kui seesmiselt muudetakse nt x x0ks, siis väljastatakse ikkagi algse vaba muutujaga
            }
        }

        return kontramudel;
    }

    public String kontraNaideStringina(){

        Map<Character, Integer> kontramudel = tagastaKontramudel();

        //boolean tudengiVastuseToevaartus = ((Ekvivalents) valem).getVasakLaps().vaartusta(vaartustused); //eeldus: tudengi pakkumine on ekvivalentsi vasak pool
        //boolean oigeVastuseToevaartus = ((Ekvivalents) valem).getParemLaps().vaartusta(vaartustused);
        boolean tudengiVastuseToevaartus = pakkumine.vaartusta(vaartustused);
        boolean oigeVastuseToevaartus = vastus.vaartusta(vaartustused);
        String infoTudengile = "Sinu vastus on väärtustusel " + kontramudel + " " + tudengiVastuseToevaartus + ", aga peaks olema " + oigeVastuseToevaartus;

        return infoTudengile;
    }

    private Valem seoVabadMuutujad(Valem ekvivalentsiValem) {

        Set<Muutuja> vabadMuutujad = ekvivalentsiValem.getVabadMuutujad();

        return helper(vabadMuutujad, ekvivalentsiValem);
    }

    private Valem helper(Set<Muutuja> vabadMuutujad, Valem ekvivalentsiValem) {

        if(vabadMuutujad.isEmpty()){
            return ekvivalentsiValem;
        }
        else{
            Muutuja vabaMuutuja = vabadMuutujad.iterator().next();
            vabadMuutujad.remove(vabaMuutuja);
            return new Iga(helper(vabadMuutujad, ekvivalentsiValem), vabaMuutuja);
        }

    }

    private Valem moodustaEkvivalentsiValem(Valem pakkumine, Valem vastus) {
        return new Ekvivalents(pakkumine, vastus);
    }
}
