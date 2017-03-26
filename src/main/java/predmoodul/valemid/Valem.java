package predmoodul.valemid;

import predmoodul.termid.IndiviidTerm;

import java.util.Map;
import java.util.Set;

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

}
