package com.qingxiao.jwt.endpoint;

import com.qingxiao.jwt.security.JwtAuthenticationToken;
import com.qingxiao.jwt.security.UserContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * End-point for retrieving logged-in user details.
 * 
 * @author vladimir.stankovic
 *
 * Aug 4, 2016
 */
@RestController
public class ProfileEndpoint {

    @RequestMapping(value="/api/me", method=RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody UserContext get(JwtAuthenticationToken token) {
        return (UserContext) token.getPrincipal();
    }

    @RequestMapping(value="/api/me3", method=RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN3')")
    public @ResponseBody UserContext get3(JwtAuthenticationToken token) {
        return (UserContext) token.getPrincipal();
    }

    @RequestMapping(value="/api/me2", method=RequestMethod.GET)
    @PreAuthorize(value = "ROLE_ADMIN2")
    public @ResponseBody UserContext get2(JwtAuthenticationToken token) {
        return (UserContext) token.getPrincipal();
    }
}
