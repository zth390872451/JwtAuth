package com.qingxiao.jwt.security.jwt.verifier;

/**
 * 
 * @author vladimir.stankovic
 *
 * Aug 17, 2016
 */
public interface TokenVerifier {
    public boolean verify(String jti);
}
