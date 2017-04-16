package predmoodul.valemid;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by siiri on 13/04/17.
 */
public class TõesuspuuTipp {

    private final Valem valem;
    private final boolean tõeväärtus;
    private boolean analüüsitud;
    private boolean annabVastuolu;

    private TõesuspuuTipp vasakLaps;
    private TõesuspuuTipp paremLaps;
    private TõesuspuuTipp vanem;

    public TõesuspuuTipp(Valem valem, boolean väärtus) {
        this.valem = valem;
        this.tõeväärtus = väärtus;
    }

    public TõesuspuuTipp(TõesuspuuTipp tipp){ //copy constructor
        this.valem = tipp.valem;
        this.tõeväärtus = tipp.tõeväärtus;
        if(tipp.vasakLaps != null){
            this.setVasakLaps(new TõesuspuuTipp(tipp.vasakLaps));
        }
        if(tipp.paremLaps != null){
            this.setParemLaps(new TõesuspuuTipp(tipp.paremLaps));
        }
    }

    public TõesuspuuTipp(AtomaarneValem atomaarneValem, boolean tõeväärtus) {
        this.valem = atomaarneValem;
        this.tõeväärtus = tõeväärtus;
        this.analüüsitud = true;

    }

    public Valem getValem() {
        return valem;
    }

    public boolean getTõeväärtus() {
        return tõeväärtus;
    }

    public void setVasakLaps(TõesuspuuTipp vasakLaps) {
        this.vasakLaps = vasakLaps;
        vasakLaps.setVanem(this);
    }

    public void setParemLaps(TõesuspuuTipp paremLaps) {
        this.paremLaps = paremLaps;
        paremLaps.setVanem(this);
    }

    private void setVanem(TõesuspuuTipp vanem) {
        this.vanem = vanem;
    }

    @Override
    public String toString() {
        return "TõesuspuuTipp{" +
                "valem=" + valem +
                ", tõeväärtus=" + tõeväärtus +
                '}';
    }

    public void lisaLapsed(List<TõesuspuuTipp> lapsed) {

        if (lapsed.size() > 1) {
            setVasakLaps(lapsed.get(0));
            setParemLaps(lapsed.get(1));
        }
        else if(lapsed.size() == 1){
            setVasakLaps(lapsed.get(0));
        }
    }

    public Set<TõesuspuuTipp> getLehed() {

        if (vasakLaps == null && paremLaps == null) {
            return new HashSet<>(Arrays.asList(this));
        }

        Set<TõesuspuuTipp> lehed = new HashSet<>();

        if (paremLaps != null) {
            lehed.addAll(paremLaps.getLehed());
        }
        if (vasakLaps != null) {
            lehed.addAll(vasakLaps.getLehed());
        }
        return lehed;
    }

    public List<TõesuspuuTipp> getLapsed() {
        return Arrays.asList(vasakLaps,paremLaps).stream().filter(x -> x != null).collect(Collectors.toList());
    }

    public boolean onAnalüüsitud() {
        return analüüsitud;
    }

    public void setAnalüüsitud(boolean analüüsitud) {
        this.analüüsitud = analüüsitud;
    }
}
