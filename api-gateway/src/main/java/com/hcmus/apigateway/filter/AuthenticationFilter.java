package com.hcmus.apigateway.filter;

import com.hcmus.apigateway.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;



@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private RouterValidator routerValidator;
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0);
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }
    public AuthenticationFilter() {
        super(Config.class);
    }
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (routerValidator.isSecured.test(request)) {
                if (this.isAuthMissing(request))
                    return this.onError(exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);
                final String token = this.getAuthHeader(request).substring(7);
                if (!jwtUtils.validateJwtToken(token))
                    return this.onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);
                ServerWebExchange mutatedExchange = exchange.mutate()
                        .request(exchange.getRequest().mutate().header("id",jwtUtils.getIdFromJwtToken(token).toString())
                        .header("role",jwtUtils.getRoleFromJwtToken(token)).build()).build();
                return chain.filter(mutatedExchange);
            }
            return  chain.filter(exchange);
        };
    }
    public static class Config {
        // Add any custom configuration if needed
    }
}