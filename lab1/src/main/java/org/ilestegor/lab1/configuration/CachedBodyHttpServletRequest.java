package org.ilestegor.lab1.configuration;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Getter
public class CachedBodyHttpServletRequest extends HttpServletRequestWrapper {

    private byte[] cachedBody;

    public CachedBodyHttpServletRequest(HttpServletRequest request) {
        super(request);
    }

    private void initCachedBody() {
        if (cachedBody == null) {
            try {
                cachedBody = super.getInputStream().readAllBytes();
            } catch (Exception e) {
                cachedBody = new byte[0];
            }
        }
    }


    @Override
    public ServletInputStream getInputStream() {
        initCachedBody();
        ByteArrayInputStream bais = new ByteArrayInputStream(cachedBody);
        return new ServletInputStream() {
            @Override
            public int read() {
                return bais.read();
            }

            @Override
            public boolean isFinished() {
                return bais.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }
        };
    }

    @Override
    public BufferedReader getReader() {
        String enc = getCharacterEncoding();
        Charset charset = (enc != null ? Charset.forName(enc) : StandardCharsets.UTF_8);
        return new BufferedReader(new InputStreamReader(getInputStream(), charset));
    }

}