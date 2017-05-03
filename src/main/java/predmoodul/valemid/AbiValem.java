package predmoodul.valemid;

import predmoodul.termid.IndiviidTerm;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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

    public AbiValem(AbiValem av){
        this.id = av.id;
        this.argumendid = av.argumendid;
        this.valem = av.valem.koopia();
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
        //System.out.println("Abivalemi vabad muutujad on: " + vabad);
        return vabad;
    }

    @Override
    public List<TõesuspuuTipp> reegel(boolean tõeväärtus, Set<Character> puusEsinenudTermid, Set<Termikuulaja> kuulajad) {

        throw new NotImplementedException();
    }

    @Override
    public boolean equals(Valem valem) {

        if(this == valem){
            return true;
        }
        if(valem == null || this.getClass() != valem.getClass()){
            return false;
        }

        AbiValem abiValem = (AbiValem) valem;

        if(!this.id.getPredSümbol().equals(abiValem.id.getPredSümbol()) && this.id.getArgumentideArv() != abiValem.id.getArgumentideArv()){
            return false;
        }

        return this.valem.equals(abiValem.valem);
    }

    public boolean vabadeMuutujateEsinemineKorrektne() {
        //return getVabadMuutujad().containsAll(argumendid);
        return new HashSet<Character>(argumendid).equals(valem.getVabadMuutujad());
    }

    @Override
    public String dot() {
        return "Abi(" + valem.dot() + ")";
    }

    @Override
    public void uusKonstantSumbol(Character sumbol) {

        valem.uusKonstantSumbol(sumbol);
    }

    @Override
    public Valem koopia() {
        return new AbiValem(this);
    }
}
