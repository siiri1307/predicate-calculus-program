package predmoodul.valemid;

import predmoodul.termid.IndiviidTerm;

import java.util.*;

/**
 * Created by siiri on 05/03/17.
 */
public abstract class Valem extends AstNode {
    /**
     * Created by siiri on 18/03/17.
     */

    public abstract boolean vaartusta(Map<Character, Double> vaartustus);

    public abstract Set<IndiviidTerm> getIndiviidTermid();

    public abstract Set<Character> getVabadMuutujad();

    public abstract List<TõesuspuuTipp> reegel(boolean tõeväärtus, Set<Character> puusEsinenudTermid, Set<Termikuulaja> kuulajad);

    public abstract boolean equals(Valem valem);

    public abstract String dot();

    public abstract void uusKonstantSumbol(Character sumbol);

    public abstract Valem koopia();

    public Optional<Termikuulaja> getKuulaja(boolean tõeväärtus) {
        return Optional.empty();
    }
}
