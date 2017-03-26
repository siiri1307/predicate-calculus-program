package predmoodul;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.FileNotFoundException;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("answer")
public class AnswerResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getInputField() throws FileNotFoundException {

        return "<html>\n" +
                "<body>\n" +
                "<div>\n" +
                "    <form method=\"post\" >\n" +
                "        <input name=\"valem\" type=\"text\" />\n" +
                "        <input type=\"submit\" value=\"Kontrolli\" />\n" +
                "    </form>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";

        //return "Hello, Heroku!";
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public boolean postAnswer(@FormParam("valem") String valem){

        Vastus answer = new Vastus(valem);

        return answer.accepts();

        //return "OK " + valem;
    }
}
