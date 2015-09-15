package net.stickycode.bootstrap.spring4;

import java.util.Collection;

import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import net.stickycode.bootstrap.StickyBootstrap;
import net.stickycode.stereotype.StickyComponent;
import net.stickycode.stereotype.StickyDomain;
import net.stickycode.stereotype.StickyPlugin;

public class Spring4StickyBootstrap
    implements StickyBootstrap {

  private Logger log = LoggerFactory.getLogger(getClass());

  private GenericApplicationContext context;

  public Spring4StickyBootstrap() {
    this.context = new GenericApplicationContext();
    scan("net.stickycode");
  }

  public Spring4StickyBootstrap(GenericApplicationContext context) {
    this.context = context;
    scan("net.stickycode");
  }

  public Spring4StickyBootstrap(String... paths) {
    this(new GenericApplicationContext());
    if (paths != null && paths.length > 0)
      scan(paths);
  }

  @Override
  public StickyBootstrap scan(String... paths) {
    ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(context, false);
    scanner.setScopeMetadataResolver(new StickyScopeMetadataResolver());
    scanner.addIncludeFilter(new AnnotationTypeFilter(StickyComponent.class));
    scanner.addIncludeFilter(new AnnotationTypeFilter(StickyPlugin.class));
    scanner.addIncludeFilter(new AnnotationTypeFilter(StickyDomain.class));
    scanner.addIncludeFilter(new AnnotationTypeFilter(Component.class));
    scanner.addIncludeFilter(new AnnotationTypeFilter(Singleton.class));
    scanner.scan(paths);
    return this;
  }

  public AutowireCapableBeanFactory getAutowirer() {
    if (!context.isActive())
      context.refresh();

    return context.getAutowireCapableBeanFactory();
  }

  @Override
  public StickyBootstrap inject(Object value) {
    getAutowirer().autowireBean(value);
    return this;
  }

  @Override
  public <T> T find(Class<T> type) {
    return context.getBean(type);
  }

  @Override
  public boolean canFind(Class<?> type) {
    return context.getBeanNamesForType(type).length > 0;
  }

  @Override
  public Object getImplementation() {
    return context;
  }

  @Override
  public void registerSingleton(String beanName, Object bean, Class<?> type) {
    log.debug("registering bean '{}' of type '{}'", beanName, type.getName());
    context.getBeanFactory().initializeBean(bean, beanName);
    context.getBeanFactory().registerSingleton(beanName, bean);
    // beans that get pushed straight into the context need to be attached to destructive bean post processors
    context.getDefaultListableBeanFactory().registerDisposableBean(
        beanName, new DisposableBeanAdapter(bean, beanName, context));
  }

  @Override
  public void registerType(String beanName, Class<?> type) {
    log.debug("registering definition '{}' for type '{}'", beanName, type.getName());
    GenericBeanDefinition bd = new GenericBeanDefinition();
    bd.setBeanClass(type);
    bd.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
    context.getDefaultListableBeanFactory().registerBeanDefinition(beanName, bd);
  }

  @Override
  public void shutdown() {
    context.close();
  }

  @Override
  public StickyBootstrap scan(Collection<String> packageFilters) {
    scan(packageFilters.toArray(new String[packageFilters.size()]));
    return this;
  }

  @Override
  public void extend(Object extension) {
  }

  @Override
  public void start() {
    context.refresh();
  }

}
