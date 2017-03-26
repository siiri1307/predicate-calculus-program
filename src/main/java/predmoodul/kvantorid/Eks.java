package predmoodul.kvantorid;

/**
 * Created by siiri on 09/03/17.
 */
public class Eks implements Kvantor {

    private Character indiviidmuutuja;

    public Eks(Character indiviidmuutuja){
        this.indiviidmuutuja = indiviidmuutuja;
    }

    @Override
    public String toString() {
        return "Eks{" +
                "indiviidmuutuja=" + indiviidmuutuja +
                '}';
    }

    @Override
    public Character getIndiviidMuutuja() {
        return this.indiviidmuutuja;
    }

    @Override
    public boolean lõpetamiseTingimus() {
        return true;
    }
}
