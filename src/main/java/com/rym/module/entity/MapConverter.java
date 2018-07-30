package com.rym.module.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.Map;

/**
 *
 * MapConverter
 * @author: zqy
 * @version
 * <pre>
 * Author	Version		Date		Changes
 * Onglu 	1.0  2018年04月27日 Created
 * </pre>
 * @since 1.
 */
public final class MapConverter implements AttributeConverter<Map<String, Object>, String> {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, Object> attribute) {
        try {
            return CollectionUtils.isEmpty(attribute) ? null : OBJECT_MAPPER.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String dbData) {
        if (!StringUtils.hasText(dbData)) {
            return ImmutableMap.of();
        }

        try {
            return OBJECT_MAPPER.readValue(dbData, new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
