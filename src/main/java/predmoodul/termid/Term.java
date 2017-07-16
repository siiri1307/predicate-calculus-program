package predmoodul.termid;

import predmoodul.valemid.AstNode;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;

/**
 * Created by siiri on 18/03/17.
 */
public abstract class Term extends AstNode {

    public abstract double vaartusta(Map<Character, Double> vaartustus);

    public abstract Set<IndiviidTerm> getIndiviidTermid();

    public double teeTehe(Map<Character, Double> vaartustus, List<Term> termid, BinaryOperator<Double> tehe) {
        return  termid.stream()
                .map(x -> x.vaartusta(vaartustus))
                .reduce(tehe).get();
    }

    public abstract String dot();

    public abstract Character getTahis();

    public abstract void uusKonstantSumbol(Character uusSumbol, Character vanaSumbol);

    public abstract void asendaTerm(Term uus, Predicate<Term> tingimus);

    public abstract Term koopia();

}
