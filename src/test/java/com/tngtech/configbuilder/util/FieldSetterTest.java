package com.tngtech.configbuilder.util;

import com.tngtech.configbuilder.annotation.valueextractor.DefaultValue;
import com.tngtech.configbuilder.configuration.BuilderConfiguration;
import com.tngtech.configbuilder.configuration.ErrorMessageSetup;
import com.tngtech.configbuilder.exception.ConfigBuilderException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FieldSetterTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static class TestConfig {
        @DefaultValue("stringValue")
        public String emptyTestString;

        @DefaultValue("stringValue")
        public String testString = "defaultValue";
    }

    private static class TestConfigForIllegalArgumentException {
        @DefaultValue("user")
        public int testInt;
    }

    private static class TestConfigWithoutAnnotations {

        public String testString = "testString";
    }


    @Mock
    private BuilderConfiguration builderConfiguration;
    @Mock
    private FieldValueExtractor fieldValueExtractor;
    @Mock
    private ErrorMessageSetup errorMessageSetup;
    @Mock
    private AnnotationHelper annotationHelper;


    @Before
    public void setUp() throws Exception {
        when(annotationHelper.fieldHasAnnotationAnnotatedWith(Matchers.any(Field.class), Matchers.any(Class.class))).thenReturn(true);
    }

    @Test
    public void testSetFieldsThrowsIllegalArgumentException() throws Exception {
        when(fieldValueExtractor.extractValue(Matchers.any(Field.class), Matchers.any(BuilderConfiguration.class))).thenReturn(null);
        when(errorMessageSetup.getErrorMessage(Matchers.any(IllegalArgumentException.class), Matchers.any(String.class), Matchers.any(String.class), Matchers.any(String.class))).thenReturn("IllegalArgumentException");

        FieldSetter<TestConfigForIllegalArgumentException> fieldSetter = new FieldSetter<TestConfigForIllegalArgumentException>(fieldValueExtractor, errorMessageSetup, annotationHelper);
        TestConfigForIllegalArgumentException testConfigForIllegalArgumentException = new TestConfigForIllegalArgumentException();

        expectedException.expect(ConfigBuilderException.class);
        expectedException.expectMessage("IllegalArgumentException");

        fieldSetter.setFields(testConfigForIllegalArgumentException, builderConfiguration);
    }

    @Test
    public void testSetFields() throws Exception {
        when(fieldValueExtractor.extractValue(Matchers.any(Field.class), Matchers.any(BuilderConfiguration.class))).thenReturn("stringValue");

        FieldSetter<TestConfig> fieldSetter = new FieldSetter<TestConfig>(fieldValueExtractor, errorMessageSetup, annotationHelper);
        TestConfig testConfig = new TestConfig();

        fieldSetter.setFields(testConfig, builderConfiguration);

        assertEquals("stringValue",testConfig.testString);
        assertEquals("stringValue",testConfig.emptyTestString);

    }

    @Test
    public void testSetEmptyFields() throws Exception {
        when(fieldValueExtractor.extractValue(Matchers.any(Field.class), Matchers.any(BuilderConfiguration.class))).thenReturn("stringValue");

        FieldSetter<TestConfig> fieldSetter = new FieldSetter<TestConfig>(fieldValueExtractor, errorMessageSetup, annotationHelper);
        TestConfig testConfig = new TestConfig();

        fieldSetter.setEmptyFields(testConfig, builderConfiguration);

        assertEquals("defaultValue",testConfig.testString);
        assertEquals("stringValue",testConfig.emptyTestString);
    }

    @Test
    public void testSetFieldsForFieldWithoutValueExtractorAnnotation() throws Exception {
        when(fieldValueExtractor.extractValue(Matchers.any(Field.class), Matchers.any(BuilderConfiguration.class))).thenReturn(null);
        when(annotationHelper.fieldHasAnnotationAnnotatedWith(Matchers.any(Field.class), Matchers.any(Class.class))).thenReturn(false);

        FieldSetter<TestConfigWithoutAnnotations> fieldSetter = new FieldSetter<TestConfigWithoutAnnotations>(fieldValueExtractor, errorMessageSetup, annotationHelper);
        TestConfigWithoutAnnotations testConfigWithoutAnnotations = new TestConfigWithoutAnnotations();

        fieldSetter.setFields(testConfigWithoutAnnotations, builderConfiguration);

        assertEquals("testString", testConfigWithoutAnnotations.testString);
    }
}
