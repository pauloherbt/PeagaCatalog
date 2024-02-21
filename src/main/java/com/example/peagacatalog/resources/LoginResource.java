package com.example.peagacatalog.resources;

import com.example.peagacatalog.dto.LoginDTO;
import com.example.peagacatalog.entities.User;
import com.example.peagacatalog.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginResource {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<String> login(@RequestBody LoginDTO dto){
        Authentication auth = UsernamePasswordAuthenticationToken.unauthenticated(dto.login(),dto.password());
        var authResp = authenticationManager.authenticate(auth);
        return ResponseEntity.ok().body(tokenService.createToken((User) authResp.getPrincipal()));
    }
}
