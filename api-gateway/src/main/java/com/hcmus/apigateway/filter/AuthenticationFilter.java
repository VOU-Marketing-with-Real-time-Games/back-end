package com.hcmus.apigateway.filter;
import com.hcmus.apigateway.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.logging.Logger;

@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {
    @Autowired
    JwtUtils jwtUtils;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Logger logger = Logger.getLogger(AuthenticationFilter.class.getName());
        String path = exchange.getRequest().getPath().toString();
        if (path.contains("v3/auth/register") || path.contains("v3/auth/login")) {
            return chain.filter(exchange);
        }
        HttpHeaders headers = exchange.getRequest().getHeaders();
        List<String> jwt = headers.get("Authorization");
        if (jwt == null || jwt.isEmpty()) {
            logger.warning("No JWT token found in request headers");
            exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        String token = jwt.get(0).substring(7);
        if (!jwtUtils.validateJwtToken(token)) {
            logger.warning("Invalid JWT token");
            exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }
    @Override
    public int getOrder() {
        return -1;
    }
}
