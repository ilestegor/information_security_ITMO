package org.ilestegor.lab1.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.time.Instant;

public class ProblemUtils {

    private ProblemUtils() {
    }


    public static ProblemDetail pb(HttpStatus status, String title, String detail, HttpServletRequest request) {
        ProblemDetail p = ProblemDetail.forStatusAndDetail(status, detail);

        p.setTitle(title);

        p.setProperty("method", request.getMethod());

        p.setProperty("timestamp", Instant.now().toString());

        return p;
    }
}
