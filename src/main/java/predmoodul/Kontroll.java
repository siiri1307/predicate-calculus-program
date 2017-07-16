package predmoodul;

import predmoodul.erindid.ErinevIndiviidideArv;
import predmoodul.kvantorid.Iga;
import predmoodul.valemid.Ekvivalents;
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
    private Map<Character, Double> vaartustused;

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

    public boolean eiOleSamavaarne2(){
        Valem valemVabadeMuutujateta = seoVabadMuutujad(valem);
        return !valemVabadeMuutujateta.vaartusta(vaartustused);
    }

    public Map<Character, Integer> tagastaKontramudel(){

        Map<Character, Integer> kontramudel = new HashMap<>();

        Set<Character> vabadMuutujad = pakkumine.getVabadMuutujad();

        //System.out.println("Vabu muutujaid on: " + vabadMuutujad.size());

        for(Character ch : vabadMuutujad){

            if(vaartustused.containsKey(ch)){
                kontramudel.put(ch, vaartustused.get(ch).intValue());
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

        Set<Character> vabadMuutujad = ekvivalentsiValem.getVabadMuutujad();

        return helper(vabadMuutujad, ekvivalentsiValem);
    }

    private Valem helper(Set<Character> vabadMuutujad, Valem ekvivalentsiValem) {

        if(vabadMuutujad.isEmpty()){
            return ekvivalentsiValem;
        }
        else{
            Character vabaMuutuja = vabadMuutujad.iterator().next();
            vabadMuutujad.remove(vabaMuutuja);
            return new Iga(helper(vabadMuutujad, ekvivalentsiValem), vabaMuutuja);
        }

    }

    private Valem moodustaEkvivalentsiValem(Valem pakkumine, Valem vastus) {
        return new Ekvivalents(pakkumine, vastus);
    }
}
