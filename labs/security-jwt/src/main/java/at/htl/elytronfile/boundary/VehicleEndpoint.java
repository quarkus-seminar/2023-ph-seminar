package at.htl.elytronfile.boundary;

import at.htl.elytronfile.entity.Vehicle;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.util.Arrays;
import java.util.List;

@Path("api/secured")
public class VehicleEndpoint {

    @Inject
    JsonWebToken jwt;

    @GET()
    @Path("permit-all")
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@Context SecurityContext ctx) {
        return getResponseString(ctx);
    }

    @GET
    @Path("roles-allowed")
    @RolesAllowed({"User", "Admin"})
    @Produces(MediaType.TEXT_PLAIN)
    public String helloRolesAllowed(@Context SecurityContext ctx) {
        return getResponseString(ctx) + ", birthdate: " + jwt.getClaim("birthdate").toString();
    }


    private String getResponseString(SecurityContext ctx) {
        String name;
        if (ctx.getUserPrincipal() == null) {
            name = "anonymous";
        } else if (!ctx.getUserPrincipal().getName().equals(jwt.getName())) {
            throw new InternalServerErrorException("Principal and JsonWebToken names do not match");
        } else {
            name = ctx.getUserPrincipal().getName();
        }
        return String.format("hello + %s,"
                        + " isHttps: %s,"
                        + " authScheme: %s,"
                        + " hasJWT: %s",
                name, ctx.isSecure(), ctx.getAuthenticationScheme(), hasJwt());
    }

    private boolean hasJwt() {
        return jwt.getClaimNames() != null;
    }

//    @RolesAllowed("user")
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<Vehicle> allVehicles() {
//        return Arrays.asList(
//            new Vehicle("Horch","P 240"),
//            new Vehicle("Tucker","'48"),
//            new Vehicle("BMW","Isetta")
//        );
//    }
//
//    @RolesAllowed("admin")
//    @GET
//    @Produces(MediaType.TEXT_PLAIN)
//    @Path("admin")
//    public String onlyAdmin() {
//        return "You are admin!";
//    }

}
