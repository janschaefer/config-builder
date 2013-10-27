package com.tngtech.configbuilder.util;

import com.tngtech.configbuilder.configuration.ErrorMessageSetup;
import com.tngtech.configbuilder.exception.ConfigBuilderException;
import com.tngtech.configbuilder.exception.NoConstructorFoundException;
import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;

public class ConstructionHelper<T> {

    private final static Logger log = Logger.getLogger(ConstructionHelper.class);

    private ErrorMessageSetup errorMessageSetup;

    public ConstructionHelper(ErrorMessageSetup errorMessageSetup) {
        this.errorMessageSetup = errorMessageSetup;
    }

    public T getInstance(Class<T> configClass, Object... objects) {
        try {
            Constructor<T> tConstructor = findSuitableConstructor(configClass, objects);
            log.info(String.format("found constructor - instantiating %s", configClass.getName()));
            tConstructor.setAccessible(true);
            return tConstructor.newInstance(objects);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new ConfigBuilderException(errorMessageSetup.getErrorMessage(e), e);
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Constructor<T> findSuitableConstructor(Class<T> configClass, Object... objects) {
        log.debug(String.format("trying to find a constructor for %s matching the arguments of build()", configClass.getName()));
        Constructor[] constructors = configClass.getDeclaredConstructors();
        for (Constructor<T> constructor : constructors) {
            if (constructorIsMatching(constructor, objects)) {
                return constructor;
            }
        }
        throw new NoConstructorFoundException(errorMessageSetup.getErrorMessage(NoConstructorFoundException.class));
    }

    private boolean constructorIsMatching(Constructor<T> constructor, Object... objects) {
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        if (parameterTypes.length != objects.length) {
            return false;
        } else {
            boolean constructorIsMatching = true;
            for (int i = 0; i < parameterTypes.length; i++) {
                constructorIsMatching &= parameterTypes[i].isAssignableFrom(objects[i].getClass());
            }
            return constructorIsMatching;
        }
    }
}
