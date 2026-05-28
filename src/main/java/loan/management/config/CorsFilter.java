package loan.management.config;

import io.quarkus.vertx.web.RouteFilter;
import io.vertx.ext.web.RoutingContext;

public class CorsFilter {

    private static final String FRONTEND_URL =
            System.getenv().getOrDefault("FRONTEND_URL", "http://localhost:5173");

    // Menggunakan @RouteFilter dengan priority tinggi agar berjalan paling awal
    @RouteFilter(100)
    void myCorsFilter(RoutingContext rc) {

        // Tambahkan header CORS ke objek HTTP Response langsung
        rc.response().headers()
                .set("Access-Control-Allow-Origin", FRONTEND_URL)
                .set("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                .set("Access-Control-Allow-Credentials", "true");

        // Jika request berupa OPTIONS (Preflight), langsung sudahi di sini
        if ("OPTIONS".equalsIgnoreCase(rc.request().method().name())) {
            rc.response().setStatusCode(200).end();
        } else {
            // Lanjutkan request ke filter berikutnya / @RolesAllowed
            rc.next();
        }
    }
}