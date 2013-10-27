package com.tngtech.configbuilder.util;

import com.tngtech.configbuilder.FieldValueProvider;
import com.tngtech.configbuilder.annotation.propertyloaderconfiguration.PropertyLoaderConfigurationAnnotation;
import com.tngtech.configbuilder.annotation.valueextractor.CommandLineValue;
import com.tngtech.configbuilder.annotation.valueextractor.PropertyValue;
import com.tngtech.configbuilder.annotation.valueextractor.ValueExtractorAnnotation;
import com.tngtech.configbuilder.annotation.valuetransformer.ValueTransformer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class AnnotationHelperTest {

    public class TestConfig {
        public class ValueProviderTestClass implements FieldValueProvider<Object> {
            public Object getValue(String fieldString) {
                return "testString";
            }
        }

        @PropertyValue("testField")
        @CommandLineValue(shortOpt = "t", longOpt = "testField")
        @ValueTransformer(ValueProviderTestClass.class)
        private Collection<String> testField;
    }


    private AnnotationHelper annotationHelper;
    private Field field;
    private Class<? extends Annotation>[] annotationOrder = new Class[]{CommandLineValue.class, PropertyValue.class};

    @Before
    public void setUp() throws Exception {
        field = TestConfig.class.getDeclaredField("testField");
        annotationHelper = new AnnotationHelper();
    }

    @Test
    public void testGetAnnotationsAnnotatedWith() throws Exception {
        List<Annotation> result = annotationHelper.getAnnotationsAnnotatedWith(field.getDeclaredAnnotations(), ValueExtractorAnnotation.class);
        assertTrue(result.contains(field.getAnnotation(CommandLineValue.class)));
        assertTrue(result.contains(field.getAnnotation(PropertyValue.class)));

        assertFalse(result.contains(field.getAnnotation(ValueTransformer.class)));
    }


    @Test
    public void testGetAnnotationsInOrder() throws Exception {
        List<Annotation> orderList = Arrays.asList(field.getAnnotation(CommandLineValue.class), field.getAnnotation(PropertyValue.class));
        List<Annotation> result = annotationHelper.getAnnotationsInOrder(field, annotationOrder);
        assertEquals(orderList, result);
    }

    @Test
    public void testGetFieldsAnnotatedWith() throws Exception {
        assertTrue(annotationHelper.getFieldsAnnotatedWith(TestConfig.class, PropertyValue.class).contains(field));
    }

    @Test
    public void testFieldHasAnnotation() throws Exception {
        assertTrue(annotationHelper.fieldHasAnnotationAnnotatedWith(field, ValueExtractorAnnotation.class));
        assertFalse(annotationHelper.fieldHasAnnotationAnnotatedWith(field, PropertyLoaderConfigurationAnnotation.class));
    }
}
