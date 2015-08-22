package net.stickycode.bootstrap.spring4;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

import net.stickycode.bootstrap.BeanNotFoundFailure;
import net.stickycode.bootstrap.ComponentContainer;
import net.stickycode.stereotype.StickyComponent;

@StickyComponent
public class SpringComponentContainer
    implements ComponentContainer {

  @Inject
  private ApplicationContext factory;

  @Override
  public void inject(Object value) {
    factory.getAutowireCapableBeanFactory().autowireBean(value);
  }

  @Override
  public <T> T find(Class<T> type) {
    try {
      return factory.getBean(type);
    }
    catch (NoSuchBeanDefinitionException e) {
      try {
        Map<String, T> beans = factory.getBeansOfType(type);
        if (beans.values().isEmpty())
          throw new BeanNotFoundFailure(e, type);

        throw new BeanNotFoundFailure(type, beans.values());
      }
      catch (NoSuchBeanDefinitionException e2) {
        throw new BeanNotFoundFailure(e2, type);
      }
    }
  }

}
