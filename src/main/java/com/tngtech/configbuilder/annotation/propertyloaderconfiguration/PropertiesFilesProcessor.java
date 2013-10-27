package com.tngtech.configbuilder.annotation.propertyloaderconfiguration;


import com.tngtech.propertyloader.PropertyLoader;

import java.lang.annotation.Annotation;
import java.util.Arrays;

public class PropertiesFilesProcessor implements IPropertyLoaderConfigurationProcessor {

    public void configurePropertyLoader(Annotation annotation, PropertyLoader propertyLoader) {
        propertyLoader.withBaseNames(Arrays.asList(((PropertiesFiles) annotation).value()));
    }
}
