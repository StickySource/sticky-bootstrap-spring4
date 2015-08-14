package net.stickycode.bootstrap.spring4;

import javax.inject.Inject;

import net.stickycode.bootstrap.ComponentContainer;
import net.stickycode.stereotype.StickyComponent;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

@StickyComponent
public class SpringComponentContainer
    implements ComponentContainer {

  @Inject
  private AutowireCapableBeanFactory factory;

  @Override
  public void inject(Object value) {
    factory.autowireBean(value);
  }

  @Override
  public <T> T find(Class<T> type) {
    return factory.getBean(type);
  }

}
