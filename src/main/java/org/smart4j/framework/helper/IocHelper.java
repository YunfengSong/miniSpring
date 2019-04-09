package org.smart4j.framework.helper;

import org.smart4j.framework.util.ArrayUtil;
import org.smart4j.framework.util.CollectionUtil;
import org.smart4j.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;


public final class IocHelper {
    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();

        if (CollectionUtil.isNotEmpty(beanMap)) {
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                Class<?> beanClass = beanEntry.getClass();
                Object beanInstance = beanEntry.getValue();
                Field[] fields = beanClass.getFields();
                if (ArrayUtil.isNotEmpty(fields)) {
                    for (Field beanField : fields) {
                        if (beanField.isAnnotationPresent(Inject.class)) {
                            Class<?> beanFieldClass = beanField.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if (beanFieldInstance != null) {
                                ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                            }
                        }
                    }
                }

            }

        }
    }
}
