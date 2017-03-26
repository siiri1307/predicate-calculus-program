package predmoodul.valemid;

import java.util.List;

/**
 * Created by siiri on 22/03/17.
 */
public class Vaartus {

    private Valem valem;
    private List<Character> argumendid;

    public Vaartus(Valem valem, List<Character> argumendid) {
        this.valem = valem;
        this.argumendid = argumendid;
    }

    public Valem getValem() {
        return valem;
    }

    public List<Character> getArgumendid() {
        return argumendid;
    }
}
