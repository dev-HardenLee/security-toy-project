package com.example.test.enumeration;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;

@Getter
public enum RoleType {
	
	ROLE_ADMIN    ("ROLE_ADMIN"    , "최고 관리자"),
	ROLE_LEADER   ("ROLE_LEADER"   , "팀장"),
	ROLE_SYSTEM   ("ROLE_SYSTEM"   , "시스템 관리자"),
	ROLE_MANAGER  ("ROLE_MANAGER"  , "매니저"),
	ROLE_USER     ("ROLE_USER"     , "일반 유저"),
	ROLE_ANONYMOUS("ROLE_ANONYMOUS", "익명 유저");
	
	private String role;
	
	private String type;
	
	private static final Map<String, RoleType> map = Collections.unmodifiableMap(
				Stream.of(values()).collect(Collectors.toMap(roleType -> roleType.role, roleType -> roleType))
			);
	
	private RoleType(String role, String type) {
		this.role = role;
		this.type = type;
	}
	
	public static RoleType findByKey(String key) {
		return map.get(key);
	}// findByKey
	
	@JsonCreator
	public static RoleType from(String text) {
		return findByKey(text);
	}// from
	
}// RoleType










