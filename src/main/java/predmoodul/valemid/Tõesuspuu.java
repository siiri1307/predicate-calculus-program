package predmoodul.valemid;

import predmoodul.kvantorid.Eks;
import predmoodul.kvantorid.Iga;

import java.util.*;

/**
 * Created by siiri on 13/04/17.
 */
public class Tõesuspuu {

    private final static Map<TõesuspuuTipp, Integer> numbrid = new HashMap<>();
    private int tootlemiseNo = 1; //mitmenda jarjekorrast välja võeti
    private boolean eiOleAegunud = true;

    private final TõesuspuuTipp juurtipp;

    private Set<TõesuspuuTipp> vaaraksMuutvadVaartustused = new HashSet<>();
    private Set<Muutuja> puusEsinenudTermid = new HashSet<>();
    //private Set<Termikuulaja> kuulajad = new HashSet<>();

    private Tõesuspuu(TõesuspuuTipp juurtipp) {
        this.juurtipp = juurtipp;
    }

    public static Tõesuspuu looTõesuspuu(Valem valem, boolean vaartus) {
        return new Tõesuspuu(new TõesuspuuTipp(valem, vaartus));
    }

    public void looPuu() {

        lisaTipp(juurtipp);

    }

    public void lisaTipp(TõesuspuuTipp juurtipp){

        long algus = System.currentTimeMillis();
        long lopp = algus + 5*1000;
        Queue<NummerdatudTõesuspuuTipp> jrk = new PriorityQueue<>(new TippudeVordleja());

        //Set<TõesuspuuTipp> eemaldatudTipud = new HashSet<>();

        jrk.add(new NummerdatudTõesuspuuTipp(juurtipp));

        while(!jrk.isEmpty() && eiOleAegunud){
            eiOleAegunud = System.currentTimeMillis() < lopp;
            if (jrk.size() % 100 == 0) {
                System.out.printf("Järjekord on %d \n", jrk.size());
            }
            TõesuspuuTipp tipp = jrk.remove().getTipp();
            numbrid.put(tipp, tootlemiseNo++);
            //System.out.println("Eemaldasin tipu: " + tipp);
            //eemaldatudTipud.add(tipp);


            if(tipp.sisaldabVastuolu()){ //kui tipp annab vastuolu, siis ei lisa teda töödeldavate tippude järjekorda
                continue;
            }

            if(tipp.onAnalüüsitud()){
                continue;
            }


            Set<Termikuulaja> kuulajad = tipp.getKuulajad(); //tagasta tipu ja tema vanemate Termikuulajad. Eesmärk leida: ∃xF(x) = 0, ∀xF(x) = 1.

            /*Collection<TõesuspuuTipp> lehed = tipp.getLehed();
            Set<Muutuja> tipuAllpuudeMuutujad = new HashSet<Muutuja>();
            for(TõesuspuuTipp leht : lehed){
                tipuAllpuudeMuutujad.addAll(leht.getTermid());
            }*/

            Set<Muutuja> harusEsinenudTermid = tipp.getTermid(); //vabad muutujad
            Set<Muutuja> uuedTermid = new HashSet<>(tipp.getTermid());
            uuedTermid.removeAll(tipp.getVanem() == null ? tipp.getValem().getVabadMuutujad() : tipp.getVanem().getTermid()); //jäta välja vanema termid
            for (Muutuja c : uuedTermid) { //iga uue konstantssümboli kohta, mis tipuga tuli, lisa puusse analüüsisamm.
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
                for (Muutuja c : tipp.getTermid()) { //kõik termid
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

            /*if(alampuud.size() == 0 && tipp.getLapsed().size() == 0 && !tipp.sisaldabVastuolu()){ //haru on lõpetatud, kuid mitte vastuoluline
                //System.out.println("Lõpetatud haru leht " + tipp.toString());
                System.out.println("Lõpetatud tipp: " + tipp.toString());
                vaaraksMuutvadVaartustused = tipp.lisaVaaraksMuutvadVaartustused();
                //tipp.teavitaAtomaarsestValemist();
                System.out.println("Lausemuutujad: " + vaaraksMuutvadVaartustused.size());
                //return;
                throw new RuntimeException("Lõpetatud, kuid mitte-vastuoluline haru");
            }*/
            if(alampuud.size() == 0){
                continue;
            }

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

    private void lisaJärjekorda(Queue<NummerdatudTõesuspuuTipp> jrk, TõesuspuuTipp tõesuspuuTipp) {

        jrk.add(new NummerdatudTõesuspuuTipp(tõesuspuuTipp));

        for (TõesuspuuTipp tipp : tõesuspuuTipp.getLapsed()) {
            lisaJärjekorda(jrk, tipp);
        }
    }

    public TõesuspuuTipp getJuurtipp() {
        return juurtipp;
    }

    public Set<Valem> vaartustusedVastavaltEeldusele(){

        Set<Map<String, Boolean>> vaartustused = new HashSet<>();

        Set<Valem> atomaarsedValemid = new HashSet<>();

        Collection<TõesuspuuTipp> lehedPuus = juurtipp.getLehed();

        System.out.println("Selles puus on " + lehedPuus.size() + " lehte.");

        for(TõesuspuuTipp leht : lehedPuus){

            System.out.println("Leidsin lehe: " + leht.toString());

            if(!leht.sisaldabVastuolu()){
                System.out.println("Leidsin väärtustuse mil valem on väär");
                atomaarsedValemid.addAll(leht.lisaVaaraksMuutvadVaartustused());
                //vaartustused.add(leht.tagastaVaartustused());
            }
        }

        //return vaartustused;
        return atomaarsedValemid;
    }


    private String valmistaDotFormaat(TõesuspuuTipp tipp){

        Queue<TõesuspuuTipp> järjekord = new LinkedList<>();

        järjekord.add(tipp);

        StringBuilder sisend = new StringBuilder();

        while(!järjekord.isEmpty()){
            TõesuspuuTipp puuTipp = järjekord.remove();
            sisend.append(puuTipp.dotFormaat(numbrid.getOrDefault(puuTipp, -1).toString()));
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

    public boolean eiOleAegunud() {
        return eiOleAegunud;
    }

    @Override
    public String toString() {

        return "Tõesuspuu{}";
    }

    private static class TippudeVordleja implements Comparator<NummerdatudTõesuspuuTipp> {
        @Override
        public int compare(NummerdatudTõesuspuuTipp o1, NummerdatudTõesuspuuTipp o2) {
            TõesuspuuTipp t1 = o1.getTipp();
            TõesuspuuTipp tother = o2.getTipp();
            if (peabHiljemTootlema(t1) && (peabHiljemTootlema(tother))) {
                return o1.getNumber() < o2.getNumber() ? -1 : 1;
            }
            if (peabHiljemTootlema(t1)) {
                return 1; //o1 > o2
            }
            //kõrgem prioriteet = väiksem
            if (peabHiljemTootlema(tother)) {
                return -1; //o1 < o2
            }

            return jarjestusHargemisePohjal(o1, o2);

        }

        private int jarjestusHargemisePohjal(NummerdatudTõesuspuuTipp nt1, NummerdatudTõesuspuuTipp nt2){
            TõesuspuuTipp t1 = nt1.getTipp();
            TõesuspuuTipp t2 = nt2.getTipp();
            if(peabHiljemTootlemaHargnemiseTottu(t1) && peabHiljemTootlemaHargnemiseTottu(t2)){
                return nt1.getNumber() < nt2.getNumber() ? -1 : 1;
            }
            if(peabHiljemTootlemaHargnemiseTottu(t1)){
                return 1;
            }
            if(peabHiljemTootlemaHargnemiseTottu(t2)){
                return -1;
            }
            return nt1.getNumber() < nt2.getNumber() ? -1 : 1;
        }

        private boolean peabHiljemTootlemaHargnemiseTottu(TõesuspuuTipp t1) {


            return vaarKonj(t1) || toeneDisj(t1) || toeneImpl(t1) || toeneEkv(t1) || vaarEkv(t1);
        }

        private boolean vaarEkv(TõesuspuuTipp t1) {
            return t1.getValem() instanceof Ekvivalents && !t1.getTõeväärtus();
        }

        private boolean toeneEkv(TõesuspuuTipp t1) {
            return t1.getValem() instanceof Ekvivalents && t1.getTõeväärtus();
        }

        private boolean toeneImpl(TõesuspuuTipp t1) {
            return t1.getValem() instanceof Implikatsioon && t1.getTõeväärtus();
        }

        private boolean toeneDisj(TõesuspuuTipp t1) {
            return t1.getValem() instanceof Disjunktsioon && t1.getTõeväärtus();
        }

        private boolean vaarKonj(TõesuspuuTipp t1) {
            return t1.getValem() instanceof Konjuktsioon && !t1.getTõeväärtus();
        }


        private boolean peabHiljemTootlema(TõesuspuuTipp o2) {
            return leidubVäär(o2) || igaToene(o2);
        }

        private boolean igaToene(TõesuspuuTipp o1) {
            return o1.getValem() instanceof Iga && o1.getTõeväärtus();
        }

        private boolean leidubVäär(TõesuspuuTipp o2) {
            return o2.getValem() instanceof Eks && !o2.getTõeväärtus();
        }
    }

    private static class NummerdatudTõesuspuuTipp {

        private static long globaalneNumber = 0;

        private final TõesuspuuTipp tipp;
        private final long number = globaalneNumber++;

        public NummerdatudTõesuspuuTipp(TõesuspuuTipp tipp) {
            this.tipp = tipp;
        }

        public TõesuspuuTipp getTipp() {
            return tipp;
        }

        public long getNumber() {
            return number;
        }
    }
}
