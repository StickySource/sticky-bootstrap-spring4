package net.stickycode.bootstrap.spring4;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
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

  public StickyBootstrap scan(String... paths) {
    ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(context, false);
    scanner.setScopeMetadataResolver(new StickyScopeMetadataResolver());
    scanner.addIncludeFilter(new AnnotationTypeFilter(StickyComponent.class));
    scanner.addIncludeFilter(new AnnotationTypeFilter(StickyDomain.class));
    scanner.addIncludeFilter(new AnnotationTypeFilter(StickyPlugin.class));
    scanner.addIncludeFilter(new AnnotationTypeFilter(Component.class));
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

}
