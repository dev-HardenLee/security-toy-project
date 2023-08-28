package com.example.test.security.provider;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import com.example.test.dto.MemberDTO;
import com.example.test.dto.response.TokenDTO;
import com.example.test.security.service.RedisService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Log4j2
public class ApiJwtTokenProvider {
	
	@Value("${jwt.secret}")
	private String secretKey;
	
	@Value("${jwt.access.token.expire.time}")
	private long accessTokenExpireTime;
	
	@Value("${jwt.refresh.token.expire.time}")
	private long refreshTokenExpireTime;
	
	public final static String CLAM_ROLE = "ROLE";
	
	private final static long SECONDS = 1000;
	
	private final RedisService redisService;
	
	public TokenDTO createJwtToken(String username) {
		Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
		
		Date now = new Date();
		
		Date accessExpireDate   = new Date(now.getTime() + accessTokenExpireTime  * SECONDS);
		Date refresshExpireDate = new Date(now.getTime() + refreshTokenExpireTime * SECONDS);
		
		String accessToken = Jwts.builder()
				.setSubject(username)
				.setIssuedAt(now)
				.setExpiration(accessExpireDate)
				.signWith(key)
				.compact();
		
		String refreshToken = Jwts.builder()
				.setIssuedAt(now)
				.setExpiration(refresshExpireDate)
				.signWith(key)
				.compact();
		
		redisService.setValuesWithTimeout(username, refreshToken, refreshTokenExpireTime * SECONDS);
		
		return new TokenDTO(accessToken, refreshToken);
	}// createToken
	
	public String getRefreshToken(String username) {
		return redisService.getValues(username);
	}// getRefreshToken
	
	public Claims extractClaims(String jwtToken) throws ExpiredJwtException, Exception{
		Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
		
		return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(jwtToken)
				.getBody();
	}// extractUsernameByJwtToken
	
	public boolean isExpiredRefreshToken(String refreshToken) {
		Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
		
		try {
			Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(refreshToken)
				.getBody();
		}catch (ExpiredJwtException e) {
			return true;
		}
		
		return false;
	}// isExpiredRefreshToken
	
}// JwtTokenProvider




















