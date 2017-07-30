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
    private Valem ekvivalentsiValem;
    private Map<Muutuja, Double> vaartustused;

    public Kontroll(Valem pakkumine, Valem vastus) {
        this.pakkumine = pakkumine;
        this.vastus = vastus;
        this.vaartustused = new HashMap<>();
    }

    /*public Kontroll(Valem valem){

        this.valem = valem;
        this.vaartustused = new HashMap<>();
    }*/

    public boolean eiOleSamavaarne() throws ErinevIndiviidideArv {

        int indiviidideArvPakkumine = pakkumine.getVabadMuutujad().size();
        int indiviidideArvVastus = vastus.getVabadMuutujad().size();

        if(indiviidideArvPakkumine != indiviidideArvVastus) {
            throw new ErinevIndiviidideArv(indiviidideArvPakkumine, indiviidideArvVastus);
        }

        ekvivalentsiValem = moodustaEkvivalentsiValem(pakkumine, vastus);
        Valem valemVabadeMuutujateta = seoVabadMuutujad(ekvivalentsiValem);

        int kvantoriteArv = valemVabadeMuutujateta.getKvantoriteArv();
        double hulk = maxVaartusVastavaltKvantoritele(kvantoriteArv);

        return !valemVabadeMuutujateta.vaartusta(vaartustused, hulk);
    }

    public boolean eiOleSamavaarneIlmaErindita() throws ErinevIndiviidideArv { //see meetod on mõeldud väärtuste väljarvutamise testimiseks - see ei võta arvesse oodatavat predikaatide arvu

        Valem ekvivalentsiValem = moodustaEkvivalentsiValem(pakkumine, vastus);
        Valem valemVabadeMuutujateta = seoVabadMuutujad(ekvivalentsiValem);
        int kvantoriteArv = valemVabadeMuutujateta.getKvantoriteArv();
        double hulk = maxVaartusVastavaltKvantoritele(kvantoriteArv);
        return !valemVabadeMuutujateta.vaartusta(vaartustused, hulk);
    }

    private double maxVaartusVastavaltKvantoritele(int kvantoriteArv) {


        if(kvantoriteArv < 6){
            return 5000.0;
        }
        if(kvantoriteArv < 10){
            return 1000.0;
        }
        if(kvantoriteArv < 13){
            return 20.0;
        }
        else{
            return 100.0;
        }
    }

    /*public boolean eiOleSamavaarne2(){
        Valem valemVabadeMuutujateta = seoVabadMuutujad(valem);
        return !valemVabadeMuutujateta.vaartusta(vaartustused);
    }*/

    public Map<Character, Integer> tagastaKontramudel(){

        Map<Character, Integer> kontramudel = new HashMap<>();

        Set<Muutuja> vabadMuutujad = vastus.getVabadMuutujad();

        for(Muutuja ch : vabadMuutujad){

            if(vaartustused.containsKey(ch)){
                kontramudel.put(ch.getTahis(), vaartustused.get(ch).intValue()); //seesmiselt hoitakse muutujat kujul tähis + nr, väljastatakse aga algne vaba muutuja
            }
        }

        return kontramudel;
    }


    public String kontraNaideStringina(){

        Map<Character, Integer> kontramudel = tagastaKontramudel();

        //boolean tudengiVastuseToevaartus = pakkumine.vaartusta(vaartustused);
        //boolean oigeVastuseToevaartus = vastus.vaartusta(vaartustused);

        String infoTudengile = "Sinu vastus on väärtustusel " + kontramudel + " " + ((Ekvivalents)ekvivalentsiValem).getVasakuToevaartus() +
                ", aga peaks olema " + ((Ekvivalents)ekvivalentsiValem).getParemaToevaartus();

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
