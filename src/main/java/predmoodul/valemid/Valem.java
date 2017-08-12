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

    public abstract boolean vaartusta(Map<Muutuja, Double> vaartustus, double maxVaartus);

    public abstract Set<IndiviidTerm> getIndiviidTermid();

    public abstract Set<Muutuja> getVabadMuutujad();

    public abstract Set<Muutuja> getSeotudMuutujad();

    public abstract int getKvantoriteArv();

    public abstract List<TõesuspuuTipp> reegel(boolean tõeväärtus, Set<Muutuja> puusEsinenudTermid, Set<Termikuulaja> kuulajad, Set<Muutuja> harusEsinenudTermid);

    public abstract String dot();

    public abstract void uusKonstantSumbol(Muutuja uusSumbol, Muutuja vanaSumbol); //boolean vahetaKvantoriSees

    public abstract void asendaTerm(Term uus, Predicate<Term> tingimus);

    public abstract Valem koopia();

    public Optional<Termikuulaja> getKuulaja(boolean tõeväärtus) {
        return Optional.empty();
    }
}
