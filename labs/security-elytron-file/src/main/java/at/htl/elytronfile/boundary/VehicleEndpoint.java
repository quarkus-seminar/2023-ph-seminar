package at.htl.elytronfile.boundary;

import at.htl.elytronfile.entity.Vehicle;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

@Path("api")
public class VehicleEndpoint {

    @RolesAllowed("user")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Vehicle> allVehicles() {
        return Arrays.asList(
            new Vehicle("Horch","P 240"),
            new Vehicle("Tucker","'48"),
            new Vehicle("BMW","Isetta")
        );
    }

    @RolesAllowed("admin")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("admin")
    public String onlyAdmin() {
        return "You are admin!";
    }

}
