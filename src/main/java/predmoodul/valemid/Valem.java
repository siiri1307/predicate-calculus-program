package predmoodul.valemid;

import predmoodul.termid.IndiviidTerm;
import predmoodul.termid.Term;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

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

    public abstract List<TõesuspuuTipp> reegel(boolean tõeväärtus, Set<Character> puusEsinenudTermid, Set<Termikuulaja> kuulajad, Set<Character> harusEsinenudTermid);

    public abstract String dot();

    public abstract void uusKonstantSumbol(Character uusSumbol, Character vanaSumbol);

    public abstract void asendaTerm(Term uus, Predicate<Term> tingimus);

    public abstract Valem koopia();

    public Optional<Termikuulaja> getKuulaja(boolean tõeväärtus) {
        return Optional.empty();
    }
}
