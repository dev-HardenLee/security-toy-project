package com.example.test.security.service;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RedisService {
	
	private final RedisTemplate<String, String> redisTemplate;
	
	@Transactional
	public void setValues(String key, String value) {
		redisTemplate.opsForValue().set(key, value);
	}// setValues
	
	@Transactional // 만료시간 설정 -> 자동 삭제
	public void setValuesWithTimeout(String key, String value, long timeout) {
		redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MILLISECONDS);
	}// setValuesWithTimeout
	
	public String getValues(String key) {
		return redisTemplate.opsForValue().get(key);
	}// getValues
	
	@Transactional
	public void deleteValues(String key) {
		redisTemplate.delete(key);
	}// deleteValues
	
	
	
}// RedisService





















