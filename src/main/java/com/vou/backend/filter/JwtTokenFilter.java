package com.vou.backend.filter;

import com.vou.backend.user.model.User;
import com.vou.backend.utils.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws IOException {
        try {
            if (isByPassToken(request)) {
                filterChain.doFilter(request, response);
                return;
            }
            final String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Missing or invalid Authorization header");
                return;
            }

            final String token = authHeader.substring(7);
            final String userIdentifier = jwtTokenUtil.extractEmail(token);
            if (userIdentifier != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User userDetails = (User) userDetailsService.loadUserByUsername(userIdentifier);
                if (jwtTokenUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    // set user
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("Error in JWT Filter: ", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    private boolean isByPassToken(@NonNull HttpServletRequest request) {
        return true;
//        final List<Pair<String, String>> byPassTokens = Arrays.asList(
//                Pair.of("/auth/login", "POST"),
//                Pair.of("/auth/forgot-password", "POST"),
//                Pair.of("/users/**", "GET"),
//                Pair.of("/users/register", "POST"),
//                Pair.of("/auth/reset-password", "POST"),
//                Pair.of("/auth/reset-password", "POST")
//        );
//        String requestServletPath = request.getServletPath();
//        String requestMethod = request.getMethod();
//        AntPathMatcher pathMatcher = new AntPathMatcher();
//        for (Pair<String, String> entry : byPassTokens) {
//            String path = entry.getFirst();
//            String method = entry.getSecond();
//            if (pathMatcher.match(path, requestServletPath) && requestMethod.equalsIgnoreCase(method)) {
//                return true;
//            }
//        }
//        return false;
    }
}
