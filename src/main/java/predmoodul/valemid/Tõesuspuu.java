package predmoodul.valemid;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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

    @Override
    public String toString() {

        return "Tõesuspuu{}";
    }
}
