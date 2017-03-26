package predmoodul.valemid;

import predmoodul.termid.IndiviidTerm;

import java.util.*;

/**
 * Created by siiri on 05/03/17.
 */
public class AbiValem extends Valem {

    private ValemiID id;
    //private String ID;
    private List<Character> argumendid;
    private Valem valem;

    /*public AbiValem(String ID, List<Character> arguments, Valem valem) {
        this.ID = ID;
        this.arguments = arguments;
        this.valem = valem;
    }*/

    public AbiValem(ValemiID id, List<Character> argumendid, Valem valem) {
        this.id = id;
        this.argumendid = argumendid;
        this.valem = valem;
    }

    //public String getID() {
      //  return ID;
    //}

    public List<Character> getArguments() {
        return argumendid;
    }


    public ValemiID getId() {
        return id;
    }

    public Valem getValem() {
        return valem;
    }

    @Override
    public List<Object> getChildren() {
        return Arrays.asList((Object)id, argumendid, valem);
    }

    @Override
    public boolean vaartusta(Map<Character, Double> vaartustus) {
        return valem.vaartusta(vaartustus);
    }

    @Override
    public Set<IndiviidTerm> getIndiviidTermid() {
        return valem.getIndiviidTermid();
    }

    @Override
    public Set<Character> getVabadMuutujad() {
        Set<Character> vabad = valem.getVabadMuutujad();
        System.out.println("Abivalemi vabad muutujad on: " + vabad);
        return vabad;
    }

    public boolean vabadeMuutujateEsinemineKorrektne() {
        //return getVabadMuutujad().containsAll(argumendid); //See peaks olema õige kui indiviidmuutuja x võib esineda valemis nii kinnise kui seotuna
        return new HashSet<Character>(argumendid).equals(valem.getVabadMuutujad());
    }

}
