package com.qingxiao.jwt.endpoint;


import com.qingxiao.jwt.dto.LoginRequest;
import com.qingxiao.jwt.dto.token.AccessJwtToken;
import com.qingxiao.jwt.dto.token.JwtTokenFactory;
import com.qingxiao.jwt.entity.User;
import com.qingxiao.jwt.entity.UserRole;
import com.qingxiao.jwt.repository.UserRepository;
import com.qingxiao.jwt.security.UserContext;
import com.qingxiao.jwt.security.jwt.JwtAuthenticationProvider;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * @Desc 认证登录接口(获取AccessToken)
 */
@RestController
@RequestMapping("/api")
public class LoginEndpoint {

	private static final Logger log = LoggerFactory.getLogger(LoginEndpoint.class);
	@Autowired
	private JwtAuthenticationProvider jwtAuthenticationProvider;
	@Autowired
	private JwtTokenFactory jwtTokenFactory;
	@Autowired
	private UserRepository userRepository;

	private static Logger logger = LoggerFactory.getLogger(LoginEndpoint.class);
	@RequestMapping(value = "/auth/login",method = RequestMethod.POST)
	public Object etToken(@RequestBody LoginRequest loginRequest){
		UsernamePasswordAuthenticationToken token = null;
		if (!StringUtils.isEmpty(loginRequest.getUsername())) {
			token = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
		} else {
			token = new UsernamePasswordAuthenticationToken(loginRequest.getClientId(), loginRequest.getClientSecret());
		}
		Optional<User> user = userRepository.findByUsername(loginRequest.getClientId());
		List<UserRole> userRoles = user.get().getRoles();
		String subject = user.get().getUsername();
		List<GrantedAuthority> authorities = userRoles.stream().map(userRole -> new SimpleGrantedAuthority(userRole.getRole().authority())).collect(Collectors.toList());
		/*for (UserRole userRole : userRoles){
			String authority = userRole.getRole().authority();
			SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
		}
		List<String> scopes = new ArrayList<>();
		scopes.add("ROLE_ADMIN");
		scopes.add("ROLE_PREMIUM_MEMBER");
		List<GrantedAuthority> authorities = scopes.stream()
				.map(authority -> new SimpleGrantedAuthority(authority))
				.collect(Collectors.toList());*/
		UserContext context = UserContext.create(loginRequest.getClientId(), authorities);
		AccessJwtToken accessJwtToken = jwtTokenFactory.createAccessJwtToken(context);
		return accessJwtToken;
	}


}
