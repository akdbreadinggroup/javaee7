package de.akdb.oesio.persistence.webshop;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/order")
public class ProductRest {

    @Inject
    private ProductService productService;

    public ProductRest() {
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/all")
    public String getAllProducts() {
        return "<html lang=\"en\"><body>" + productService.readAllProducts() + "</body></html>";
    }

}
