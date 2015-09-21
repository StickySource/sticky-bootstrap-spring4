package net.stickycode.bootstrap.spring4;

import javax.inject.Provider;

import org.springframework.beans.factory.FactoryBean;

public class FactoryBeanProviderAdapter
    implements FactoryBean<Object> {

  private Provider<Object> provider;
  private Class<?> type;

  public FactoryBeanProviderAdapter(Provider<Object> provider, Class<?> type) {
    this.provider= provider;
    this.type = type;
  }

  @Override
  public Object getObject()
      throws Exception {
    return provider.get();
  }

  @Override
  public Class<?> getObjectType() {
    return type;
  }

  @Override
  public boolean isSingleton() {
    return false;
  }

}
