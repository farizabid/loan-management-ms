package loan.management.config;

import io.quarkus.vertx.http.runtime.filters.Filters;
import jakarta.enterprise.event.Observes;

public class CorsFilter {

    private static final String FRONTEND_URL =
            System.getenv().getOrDefault("FRONTEND_URL", "http://localhost:5173");

    // Menggunakan Event Observer tingkat Vert.x HTTP untuk mendaftarkan filter global
    public void registerCorsFilter(@Observes Filters filters) {
        filters.register(rc -> {

            // Tambahkan header CORS langsung ke end-response (berlaku untuk status 401/403/500)
            rc.response().headers()
                    .set("Access-Control-Allow-Origin", FRONTEND_URL)
                    .set("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                    .set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                    .set("Access-Control-Allow-Credentials", "true");

            // Tangani Preflight OPTIONS di gerbang paling depan
            if ("OPTIONS".equalsIgnoreCase(rc.request().method().name())) {
                rc.response().setStatusCode(200).end();
            } else {
                rc.next();
            }

        }, 10_000); // Nilai 10000 menjamin ini berada di urutan teratas (paling luar) dari seluruh aplikasi
    }
}