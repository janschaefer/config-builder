package com.tngtech.configbuilder.util;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.reflections.ReflectionUtils.*;

public class AnnotationHelper {

    public List<Annotation> getAnnotationsAnnotatedWith(Annotation[] annotations, Class<? extends Annotation> annotationClass) {
        List<Annotation> result = new ArrayList<Annotation>();
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().isAnnotationPresent(annotationClass)) {
                result.add(annotation);
            }
        }
        return result;
    }

    public List<Annotation> getAnnotationsInOrder(Field field, Class<? extends Annotation>[] annotationOrder) {
        List<Annotation> result = new ArrayList<Annotation>();
        for (Class<? extends Annotation> annotationClass : annotationOrder) {
            if (field.isAnnotationPresent(annotationClass)) {
                result.add(field.getAnnotation(annotationClass));
            }
        }
        return result;
    }

    public Set<Field> getFieldsAnnotatedWith(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        return getAllFields(clazz, withAnnotation(annotationClass));
    }

    public Set<Method> getMethodsAnnotatedWith(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        return getAllMethods(clazz, withAnnotation(annotationClass));
    }

    public boolean fieldHasAnnotationAnnotatedWith(Field field, Class<? extends Annotation> annotationClass) {
        Annotation[] annotations = field.getDeclaredAnnotations();
        return !getAnnotationsAnnotatedWith(annotations, annotationClass).isEmpty();
    }
}
