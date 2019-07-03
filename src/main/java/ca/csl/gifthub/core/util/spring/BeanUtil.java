package ca.csl.gifthub.core.util.spring;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import ca.csl.gifthub.core.util.spring.exception.AutowireFactoryNotFoundException;

@Service
//experiment by deleting service annotation
//ONLY USE THIS CLASS AFTER SPRING HAS BEEN STARTED
public class BeanUtil implements ApplicationContextAware {

    private static final Logger LOG = LogManager.getLogger(BeanUtil.class);

    private static AutowireCapableBeanFactory factory;

    BeanUtil() {}

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        factory = applicationContext.getAutowireCapableBeanFactory();
    }

    public static <T> T getBean(Class<T> beanClass) {
        return getFactory().getBean(beanClass);
    }

    public static <T> T getBean(String name, Class<T> beanClass) {
        return getFactory().getBean(name, beanClass);
    }

    public static AutowireCapableBeanFactory getFactory() {
        if (factory == null) {
            LOG.error(
                    "Could not fetch bean from spring context, it has either not been created yet or BeanUtil has not bean created by spring yet");
            throw new AutowireFactoryNotFoundException();
        }
        return factory;
    }

}
