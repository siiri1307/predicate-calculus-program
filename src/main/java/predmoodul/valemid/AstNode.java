package predmoodul.valemid;

import java.util.Iterator;
import java.util.List;

/**
 * Created by siiri on 05/03/17.
 */
public abstract class AstNode {

    public abstract List<Object> getChildren();

    private String getSimpleName() {
        return this.getClass().getSimpleName();
    }

    public AstNode getChild(int i){
       return this.getChild(i);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder(getSimpleName());

        sb.append("(");

        Iterator<Object> iter = this.getChildren().iterator();

        while (iter.hasNext()) {
            Object child = iter.next();
            if (child instanceof String) {
                String str = (String)child;
                sb.append("\""
                        + str.replaceAll("\"", "\\\"")
                        .replaceAll("\r\n", "\\r\\n")
                        .replaceAll("\n", "\\n")
                        .replaceAll("\t", "\\t")
                        + "\"");
            }
            else {
                sb.append(child);
            }
            if (iter.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(")");

        return sb.toString();
    }
}
