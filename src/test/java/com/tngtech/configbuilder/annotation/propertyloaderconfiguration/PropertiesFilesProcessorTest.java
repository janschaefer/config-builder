package com.tngtech.configbuilder.annotation.propertyloaderconfiguration;

import java.util.Arrays;

import com.tngtech.propertyloader.PropertyLoader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PropertiesFilesProcessorTest {

    @Mock
    private PropertiesFiles propertiesFiles;
    @Mock
    PropertyLoader propertyLoader;

    private PropertiesFilesProcessor propertiesFilesProcessor;

    @Before
    public void setUp() throws Exception {
        propertiesFilesProcessor = new PropertiesFilesProcessor();
    }

    @Test
    public void testPropertiesFilesProcessor() {

        String[] fileNames = new String[]{"file1", "file2"};

        when(propertiesFiles.value()).thenReturn(fileNames);

        propertiesFilesProcessor.configurePropertyLoader(propertiesFiles, propertyLoader);

        verify(propertyLoader).withBaseNames(Arrays.asList(fileNames));
    }
}
