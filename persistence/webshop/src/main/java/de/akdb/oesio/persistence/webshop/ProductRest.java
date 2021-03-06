package de.akdb.oesio.persistence.webshop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.transaction.UserTransaction;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import java.util.stream.Collectors;

@Path("/order")
public class ProductRest {
    private static final Logger LOG = LoggerFactory.getLogger(ProductRest.class);
    @Inject
    private ProductService productService;
    @Inject
    private OrderCounter orderCounter;
    @Inject
    private OrderService orderService;

    @Context
    private UriInfo uriInfo;
    @Context
    private Request request;

    @Resource
    private UserTransaction transaction;

    public ProductRest() {
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/all")
    public String getAllProducts() {
        return "<html lang=\"en\"><body>" + productService.readAllProducts().stream()
                .map(this::productAlsString).collect(Collectors.joining()) + "</body></html>";
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/buy")
    public String buy(@QueryParam("buyer") String buyer, @QueryParam("id") int id, @QueryParam("amount") int amount) {
        try {
            String orderResult = orderService.buy(buyer, id, amount);
            return "<html lang=\"en\"><body>Guter Kauf. " + orderResult + "</body></html>";
        } catch (Exception e) {
            LOG.error("Exception", e);
            return "<html lang=\"en\"><body> <h1>" + e.getMessage() + "</h1></body></html>";
        }
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/count")
    public String getOrderCount() {
        return "<html lang=\"en\"><body>" + orderCounter.getCount() + "</body></html>";
    }

    private String productAlsString(Product product) {
        return "Id=" + product.getId() + ", Name=" + product.getName() + ", Preis=" + product.getPrize() + "<br>";
    }

}
