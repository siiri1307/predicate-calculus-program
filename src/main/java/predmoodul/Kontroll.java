package predmoodul;

import predmoodul.erindid.AbiValemEiOleDefineeritud;
import predmoodul.erindid.ErinevIndiviidideArv;
import predmoodul.erindid.SyntaksiViga;
import predmoodul.erindid.VaarVabadeMuutujateEsinemine;
import predmoodul.kvantorid.Iga;
import predmoodul.valemid.*;

import java.io.IOException;
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
    private boolean eiOleSamavaarne;
    private int kontrolliTulemus;

    public Kontroll(Valem pakkumine, Valem vastus) throws ErinevIndiviidideArv, VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, SyntaksiViga, IOException {

        this.pakkumine = pakkumine;
        this.vastus = vastus;
        this.vaartustused = new HashMap<>();
        kontrolliIndiviidideArvuValemites(pakkumine.getVabadMuutujad(), vastus.getVabadMuutujad());
        ekvivalentsiValem = moodustaEkvivalentsiValem(pakkumine, vastus);
        kontrolliTulemus = kontrolli(ekvivalentsiValem);
    }

    public Kontroll(String tudengiPakkumine, String oigeVastus) throws ErinevIndiviidideArv, VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, SyntaksiViga, IOException {

        Sisend sisend = new Sisend(tudengiPakkumine, oigeVastus);
        this.pakkumine = sisend.getPakkumiseValem();
        this.vastus = sisend.getOigeValem();
        //this.pakkumine = pakkumine;
        //this.vastus = vastus;
        this.vaartustused = new HashMap<>();
        kontrolliIndiviidideArvuValemites(pakkumine.getVabadMuutujad(), vastus.getVabadMuutujad());
        ekvivalentsiValem = moodustaEkvivalentsiValem(pakkumine, vastus);
        kontrolliTulemus = kontrolli(ekvivalentsiValem);
    }

    public int kontrolli(Valem ekvivalentsiValem) throws ErinevIndiviidideArv {

        Tõesuspuu tp = Tõesuspuu.looTõesuspuu(ekvivalentsiValem, false);
        tp.looPuu();
        if(tp.vaartustusedVastavaltEeldusele().isEmpty() && tp.eiOleAegunud()){
            //System.out.println("Tõesuspuu meetod tegi kindaks et samaväärsed");
            return 1; // "jah"
        }
        else{
            //System.out.println("Jõudsin väärtuste arvutamiseni");
            boolean eiOleSamavaarne = eiOleSamavaarne();
            if(eiOleSamavaarne){
                return 0; //"ei"
            }
            else{
                return 3; //"ei tea"
            }
        }
    }

    public int getKontrolliTulemus() {
        return kontrolliTulemus;
    }

    public boolean kontrolliIndiviidideArvuValemites(Set<Muutuja> pakkumiseVabadMuutujad, Set<Muutuja> oigeVabadMuutujad) throws ErinevIndiviidideArv {

        int indiviidideArvPakkumine = pakkumiseVabadMuutujad.size();
        int indiviidideArvOige = oigeVabadMuutujad.size();

        if(indiviidideArvPakkumine != indiviidideArvOige){
            throw new ErinevIndiviidideArv(indiviidideArvPakkumine, indiviidideArvOige, oigeVabadMuutujad);
        }
        else{
            for(Muutuja m : oigeVabadMuutujad){
                if(!pakkumiseVabadMuutujad.contains(m)){
                    throw new ErinevIndiviidideArv(m);
                }
            }
        }

        return true;
    }

    public boolean eiOleSamavaarne() throws ErinevIndiviidideArv {


        kontrolliIndiviidideArvuValemites(pakkumine.getVabadMuutujad(), vastus.getVabadMuutujad());

        Valem valemVabadeMuutujateta = seoVabadMuutujad(ekvivalentsiValem);

        int kvantoriteSygavus = valemVabadeMuutujateta.getKvantoriteSygavus();
        double hulk = maxVaartusVastavaltKvantoritele(kvantoriteSygavus);
        eiOleSamavaarne = !valemVabadeMuutujateta.vaartusta(vaartustused, hulk);

        return eiOleSamavaarne;
    }

    public Valem getEkvivalentsiValem() {
        return ekvivalentsiValem;
    }

    public boolean eiOleSamavaarneIlmaErindita() throws ErinevIndiviidideArv { //see meetod on mõeldud väärtuste väljarvutamise testimiseks - see ei võta arvesse oodatavat predikaatide arvu

        Valem ekvivalentsiValem = moodustaEkvivalentsiValem(pakkumine, vastus);
        Valem valemVabadeMuutujateta = seoVabadMuutujad(ekvivalentsiValem);
        int kvantoriteArv = valemVabadeMuutujateta.getKvantoriteArv();
        double hulk = maxVaartusVastavaltKvantoritele(kvantoriteArv);
        eiOleSamavaarne = !valemVabadeMuutujateta.vaartusta(vaartustused, hulk);

        return !valemVabadeMuutujateta.vaartusta(vaartustused, hulk);
    }

    public Valem getPakkumine() {
        return pakkumine;
    }

    public Valem getVastus() {
        return vastus;
    }

    private double maxVaartusVastavaltKvantoritele(int kvantoriteSygavus) {

        if(kvantoriteSygavus < 4){
            return 5000.0;
        }
        if(kvantoriteSygavus < 7){
            return 30;
        }
        if(kvantoriteSygavus < 9){
            return 10.0;
        }
        else{
            return 2.0;
        }
    }

    public Map<Character, Integer> tagastaKontramudel(){

        Map<Character, Integer> kontramudel = new HashMap<>();

        Set<Muutuja> vabadMuutujad = vastus.getVabadMuutujad();

        if(eiOleSamavaarne){

            for(Muutuja ch : vabadMuutujad){

                if(vaartustused.containsKey(ch)){
                    kontramudel.put(ch.getTahis(), vaartustused.get(ch).intValue()); //seesmiselt hoitakse muutujat kujul tähis + nr, väljastatakse aga algne vaba muutuja
                }
            }
        }

        return kontramudel;
    }


    public String kontraNaideStringina(){

        Map<Character, Integer> kontramudel = tagastaKontramudel();

        //boolean tudengiVastuseToevaartus = pakkumine.vaartusta(vaartustused);
        //boolean oigeVastuseToevaartus = vastus.vaartusta(vaartustused);

        String infoTudengile = new String();

        if(eiOleSamavaarne){

            infoTudengile = "Sinu vastus on väärtustusel " + kontramudel + " " + toevaartusEestiKeelseks(((Ekvivalents)ekvivalentsiValem).getVasakuToevaartus()) +
                    ", aga peaks olema " + toevaartusEestiKeelseks(((Ekvivalents)ekvivalentsiValem).getParemaToevaartus()) + ".";
        }

        return infoTudengile;
    }

    private String toevaartusEestiKeelseks(boolean toevaartus){

        return toevaartus ? "tõene" : "väär";
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

    public Valem moodustaEkvivalentsiValem(Valem pakkumine, Valem vastus) {
        return new Ekvivalents(pakkumine, vastus);
    }

}
