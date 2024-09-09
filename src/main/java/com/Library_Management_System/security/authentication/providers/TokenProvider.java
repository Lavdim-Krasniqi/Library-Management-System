package com.Library_Management_System.security.authentication.providers;

import com.Library_Management_System.security.authentication.utility.JwtTokenUtil;
import com.Library_Management_System.user.entity.Token;
import com.Library_Management_System.user.entity.User;
import com.Library_Management_System.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TokenProvider implements AuthenticationProvider {

    private final JwtTokenUtil tokenUtil;
    private final UserService userService;

    public TokenProvider(JwtTokenUtil tokenUtil, UserService userService){
        this.userService = userService;
        this.tokenUtil = tokenUtil;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getPrincipal();

        User user = userService.findUserByAccessToken(token);
        if (user == null) throw new BadCredentialsException("Invalid Token");
        if (user.getToken().getIsRevoked()) throw new BadCredentialsException("Token is revoked");

        if (tokenUtil.validateToken(token)) {
            return new TokenAuthentication(tokenUtil.getClaimsFromToken(token).get("username"),
                    null,
                    List.of(new SimpleGrantedAuthority(tokenUtil.getRole(token))));
        }

        if (tokenUtil.validateToken(user.getToken().getRefreshToken())) {
            String newAccessToken = tokenUtil.generateToken(user.getUsername(), user.getRole(), 2);
            String newRefreshToken = tokenUtil.generateToken(user.getUsername(), user.getRole(), 8);
            Token generatedTokenPair = new Token();
            generatedTokenPair.setAccessToken(newAccessToken);
            generatedTokenPair.setRefreshToken(newRefreshToken);
            generatedTokenPair.setIsRevoked(false);

            userService.addToken(generatedTokenPair, user.getUsername());

            TokenAuthentication tokenAuthentication = new TokenAuthentication(user.getUsername(),
                    null,
                    List.of(new SimpleGrantedAuthority(user.getRole())));
            tokenAuthentication.setDetails(newAccessToken);
            return tokenAuthentication;

        }
        throw new BadCredentialsException("Invalid Token");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(TokenAuthentication.class);
    }
}
