package predmoodul.valemid;

import java.util.*;

/**
 * Created by siiri on 13/04/17.
 */
public class Tõesuspuu {

    private final TõesuspuuTipp juurtipp;

    private Set<TõesuspuuTipp> vaaraksMuutvadVaartustused = new HashSet<>();
    private Set<Character> puusEsinenudTermid = new HashSet<>();
    //private Set<Termikuulaja> kuulajad = new HashSet<>();

    private Tõesuspuu(TõesuspuuTipp juurtipp) {
        this.juurtipp = juurtipp;
    }

    public static Tõesuspuu looTõesuspuu(Valem valem, boolean vaartus) {
        return new Tõesuspuu(new TõesuspuuTipp(valem, vaartus));
    }


    public void lisaTipp(TõesuspuuTipp juurtipp){

        Queue<TõesuspuuTipp> jrk = new LinkedList<>();

        //Set<TõesuspuuTipp> eemaldatudTipud = new HashSet<>();

        jrk.add(juurtipp);

        while(!jrk.isEmpty()){

            TõesuspuuTipp tipp = jrk.remove();
            //System.out.println("Eemaldasin tipu: " + tipp);
            //eemaldatudTipud.add(tipp);

            if(tipp.sisaldabVastuolu()){ //kui tipp annab vastuolu, siis ei lisa teda töödeldavate tippude järjekorda
                continue;
            }

            if(tipp.onAnalüüsitud()){
                continue;
            }

            Set<Termikuulaja> kuulajad = tipp.getKuulajad(); //tagasta tipu ja tema vanemate Termikuulajad. Eesmärk leida: ∃xF(x) = 0, ∀xF(x) = 1.
            Set<Character> harusEsinenudTermid = tipp.getTermid(); //vabad muutujad
            Set<Character> uuedTermid = new HashSet<>(tipp.getTermid());
            uuedTermid.removeAll(tipp.getVanem() == null ? tipp.getValem().getVabadMuutujad() : tipp.getVanem().getTermid()); //jäta välja vanema termid
            for (Character c : uuedTermid) { //iga uue konstantssümboli kohta, mis tipuga tuli, lisa puusse analüüsisamm.
                // uued termid saavad vanad faktid. Puus ei ole teadmisi uute termide kohta mis just lisati
                for (TõesuspuuTipp leht : tipp.getLehed()) {
                    if (leht.sisaldabVastuolu()) {
                        continue;
                    }
                    for (Termikuulaja kuulaja1 : kuulajad) { //itereeri üle lisandunud termide ja asenda igas vajalikus valemis sümbol uue termi vastu
                        TõesuspuuTipp uusLeht = kuulaja1.kuulaKonstantSumbolit(c);
                        if (sisaldabLehte(leht, uusLeht)) {
                            continue;
                        }
                        leht.setVasakLaps(uusLeht);
                        lisaJärjekorda(jrk, uusLeht);
                        leht = uusLeht;
                    }
                }
            }

            //System.out.println("Haru termid on: " + harusEsinenudTermid);
            //System.out.println("Puu termid on: " + puusEsinenudTermid);
            List<TõesuspuuTipp> alampuud = tipp.getValem().reegel(tipp.getTõeväärtus(), puusEsinenudTermid, kuulajad, harusEsinenudTermid);

            Optional<Termikuulaja> kuulaja = tipp.getValem().getKuulaja(tipp.getTõeväärtus()); //vanad termid saavad uue fakti. Puus ei ole uut teadmist vanade termide kohta.
            if (kuulaja.isPresent()) {
                kuulajad.add(kuulaja.get());
                for (Character c : tipp.getTermid()) { //kõik termid
                    for (TõesuspuuTipp leht : tipp.getLehed()) {
                        if (leht.sisaldabVastuolu()) {
                            continue;
                        }
                        TõesuspuuTipp uusLeht = kuulaja.get().kuulaKonstantSumbolit(c);
                        if (sisaldabLehte(leht, uusLeht)) {
                            continue;
                        }
                        leht.setVasakLaps(uusLeht);
                        lisaJärjekorda(jrk, uusLeht);
                    }
                }
            }


//            for(TõesuspuuTipp tippp : alampuud){
//                for (TõesuspuuTipp t : tippp.getLehed()) {

//  }
//                    }
//                }
//            }

            if(alampuud.size() == 0 && tipp.getLapsed().size() == 0 && !tipp.sisaldabVastuolu()){ //haru on lõpetatud, kuid mitte vastuoluline
                //System.out.println("Lõpetatud haru leht " + tipp.toString());
                vaaraksMuutvadVaartustused = tipp.lisaVaaraksMuutvadVaartustused();
                //tipp.teavitaAtomaarsestValemist();
                System.out.println("Lausemuutujad: " + vaaraksMuutvadVaartustused.size());
                //return;
                throw new RuntimeException("Lõpetatud, kuid mitte-vastuoluline haru");
            }
            if(alampuud.size() == 0){
                continue;
            }

//            for(TõesuspuuTipp tõesuspuuTipp : alampuud){
//                System.out.println("Tipp on: " + tõesuspuuTipp);
//                System.out.println("Tipu vanem on: " + tipp);
//                System.out.println("-------------------");
//                lisaJärjekorda(jrk, tõesuspuuTipp); //rekursiivne meetod, kuna alampuu võib sisaldada tippu, millele on omakorda meetodis 'reegel' seatud laps
//            }

            for (TõesuspuuTipp leht : tipp.getLehed()) {
                if(leht.sisaldabVastuolu()){
                    continue;
                }

                TõesuspuuTipp vasakLaps = new TõesuspuuTipp(alampuud.get(0));
                lisaJärjekorda(jrk, vasakLaps);
                leht.setVasakLaps(vasakLaps);

                if(alampuud.size() == 2){
                    TõesuspuuTipp paremLaps = new TõesuspuuTipp(alampuud.get(1));
                    lisaJärjekorda(jrk, paremLaps);
                    leht.setParemLaps(paremLaps);
                }

            }

            tipp.setAnalüüsitud(true);
        }
    }



    private boolean sisaldabLehte(TõesuspuuTipp leht, TõesuspuuTipp uusLeht) {
        if (leht == null) {
            return false;
        }
        return leht.sama(uusLeht) || sisaldabLehte(leht.getVanem(), uusLeht);
    }

    private void lisaJärjekorda(Queue<TõesuspuuTipp> jrk, TõesuspuuTipp tõesuspuuTipp) {

        jrk.add(tõesuspuuTipp);

        for (TõesuspuuTipp tipp : tõesuspuuTipp.getLapsed()) {
            lisaJärjekorda(jrk, tipp);
        }
    }

    public void looPuu() {
        lisaTipp(juurtipp);
    }

    public TõesuspuuTipp getJuurtipp() {
        return juurtipp;
    }

    public Set<Map<String, Boolean>> vaartustusedVastavaltEeldusele(){

        Set<Map<String, Boolean>> vaartustused = new HashSet<>();

        Collection<TõesuspuuTipp> lehedPuus = juurtipp.getLehed();

        System.out.println("Selles puus on " + lehedPuus.size() + " lehte.");

        for(TõesuspuuTipp leht : lehedPuus){

            System.out.println("Leidsin lehe: " + leht.toString());

            if(!leht.sisaldabVastuolu()){
                System.out.println("Leidsin väärtustuse mil valem on väär");
                vaartustused.add(leht.tagastaVaartustused());
            }
        }

        return vaartustused;
    }



    private String valmistaDotFormaat(TõesuspuuTipp tipp){

        Queue<TõesuspuuTipp> järjekord = new LinkedList<>();

        järjekord.add(tipp);

        StringBuilder sisend = new StringBuilder();

        while(!järjekord.isEmpty()){
            TõesuspuuTipp puuTipp = järjekord.remove();
            sisend.append(puuTipp.dotFormaat());
            if(puuTipp.getVasakLaps() != null){
                järjekord.add(puuTipp.getVasakLaps());
            }
            if(puuTipp.getParemLaps() != null){
                järjekord.add(puuTipp.getParemLaps());
            }
        }

        return sisend.toString();
    }

    public String dot() {
        if(this.juurtipp == null){
            throw new IllegalArgumentException("Tõesuspuul puudub juurtipp!");
        }
        return valmistaDotFormaat(this.juurtipp);
    }

    @Override
    public String toString() {

        return "Tõesuspuu{}";
    }
}
