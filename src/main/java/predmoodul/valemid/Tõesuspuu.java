package predmoodul.valemid;

import java.util.*;

/**
 * Created by siiri on 13/04/17.
 */
public class Tõesuspuu {

    private final TõesuspuuTipp juurtipp;

    private Tõesuspuu(TõesuspuuTipp juurtipp) {
        this.juurtipp = juurtipp;
    }

    public static Tõesuspuu looTõesuspuu(Valem valem, boolean vaartus) {
        return new Tõesuspuu(new TõesuspuuTipp(valem, vaartus));
    }


    public void lisaTipp(TõesuspuuTipp juurtipp){

        Queue<TõesuspuuTipp> jrk = new LinkedList<>();

        jrk.add(juurtipp);

        while(!jrk.isEmpty()){

            TõesuspuuTipp tipp = jrk.remove();

            if(tipp.sisaldabVastuolu()){ //kui tipp annab vastuolu, siis ei lisa teda töödeldavate tippude järjekorda
                continue;
            }

            if(tipp.onAnalüüsitud()){
                continue;
            }

            List<TõesuspuuTipp> alampuud = tipp.getValem().reegel(tipp.getTõeväärtus());

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

    private void lisaJärjekorda(Queue<TõesuspuuTipp> jrk, TõesuspuuTipp tõesuspuuTipp) {
        jrk.add(tõesuspuuTipp);
        for (TõesuspuuTipp tipp : tõesuspuuTipp.getLapsed()) {
            lisaJärjekorda(jrk, tipp);
        }
    }

    public void looPuu() {
        lisaTipp(juurtipp);
    }

    public Set<Map<String, Boolean>> vaartustusedVastavaltEeldusele(){

        Set<Map<String, Boolean>> vaartustused = new HashSet<>();

        Set<TõesuspuuTipp> lehedPuus = juurtipp.getLehed();

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
