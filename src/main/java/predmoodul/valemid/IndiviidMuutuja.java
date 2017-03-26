package predmoodul.valemid;

import java.util.Arrays;
import java.util.List;

/**
 * Created by siiri on 10/03/17.
 */
public class IndiviidMuutuja extends AstNode {

    private Character tahis;

    public IndiviidMuutuja(Character tahis){
        this.tahis = tahis;
    }

    public Character getTahis() {
        return tahis;
    }

    @Override
    public List<Object> getChildren() {
        return Arrays.asList((Object) tahis);
    }
}
