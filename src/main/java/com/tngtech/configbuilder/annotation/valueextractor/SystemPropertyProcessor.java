package com.tngtech.configbuilder.annotation.valueextractor;

import com.tngtech.configbuilder.configuration.BuilderConfiguration;

import java.lang.annotation.Annotation;

/**
 * Processes SystemPropertyValue annotations, implements IValueExtractorProcessor
 */
public class SystemPropertyProcessor implements IValueExtractorProcessor {
    public String getValue(Annotation annotation, BuilderConfiguration builderConfiguration) {
        return System.getProperty(((SystemPropertyValue) annotation).value());
    }
}
