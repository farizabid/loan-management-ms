package loan.management.config;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CorsFilter implements ContainerResponseFilter {

    private static final String FRONTEND_URL =
            System.getenv().getOrDefault("FRONTEND_URL", "http://localhost:5173");

    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) {

        responseContext.getHeaders().add("Access-Control-Allow-Origin", FRONTEND_URL);
        responseContext.getHeaders().add("Access-Control-Allow-Headers",
                "origin, content-type, accept, authorization");
        responseContext.getHeaders().add("Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS");

        // penting untuk preflight
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
    }
}