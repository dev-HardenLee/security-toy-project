package com.example.test.enumeration;

import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Getter
public enum ResourceType {
    API_RESOURCE("API_RESOURCE", "API용 resource"),
    NORMAL_RESOURCE("NORMAL_RESOURCE", "일반 resource");

    private String type;

    private String value;

    private static final Map<String ,ResourceType> map = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(resourceType -> resourceType.type, resourceType -> resourceType))
    );

    private ResourceType(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public static ResourceType findOfKey(String key) {
        return map.get(key);
    }// findOfKey

}// ResourceType
