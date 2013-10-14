package com.tngtech.configbuilder.testclasses;

import com.google.common.collect.Lists;
import com.tngtech.configbuilder.FieldValueProvider;
import com.tngtech.configbuilder.annotation.configuration.LoadingOrder;
import com.tngtech.configbuilder.annotation.propertyloaderconfiguration.PropertiesFiles;
import com.tngtech.configbuilder.annotation.propertyloaderconfiguration.PropertyExtension;
import com.tngtech.configbuilder.annotation.propertyloaderconfiguration.PropertyLocations;
import com.tngtech.configbuilder.annotation.propertyloaderconfiguration.PropertySuffixes;
import com.tngtech.configbuilder.annotation.valueextractor.CommandLineValue;
import com.tngtech.configbuilder.annotation.valueextractor.DefaultValue;
import com.tngtech.configbuilder.annotation.valueextractor.PropertyValue;
import com.tngtech.configbuilder.annotation.valuetransformer.ValueTransformer;
import com.tngtech.propertyloader.PropertyLoader;

import java.util.Collection;

@PropertyExtension("testproperties")
@PropertySuffixes(extraSuffixes = {"test"})
@PropertyLocations(resourcesForClasses = {PropertyLoader.class})
@PropertiesFiles("demoapp-configuration")
@LoadingOrder(value = {CommandLineValue.class, PropertyValue.class, DefaultValue.class})
public class TestConfig {

    public TestConfig(){

    }

    public static class PidFixFactory implements FieldValueProvider<Collection<String>> {

        public Collection<String> getValue(String optionValue) {
            Collection<String> coll = Lists.newArrayList();
            coll.add(optionValue + " success");
            return coll;
        }
    }

    @DefaultValue("3")
    private int userName;

    @PropertyValue("a")
    private String helloWorld;

    @CommandLineValue(shortOpt = "u", longOpt = "user")
    private boolean surName;

    @LoadingOrder(value = {CommandLineValue.class})
    @CommandLineValue(shortOpt = "p", longOpt = "pidFixFactory", hasArg = true)
    @ValueTransformer(PidFixFactory.class)
    private Collection<String> pidFixes;

    public void setUserName(Integer userName) {
        this.userName = userName;
    }

    public void setHelloWorld(String helloWorld) {
        this.helloWorld = helloWorld;
    }

    public void setSurName(boolean surName) {
        this.surName = surName;
    }

    public void setPidFixes(Collection<String> pidFixes) {
        this.pidFixes = pidFixes;
    }
}
