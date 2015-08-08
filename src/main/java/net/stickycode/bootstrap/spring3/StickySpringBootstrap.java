package net.stickycode.bootstrap.spring3;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import net.stickycode.stereotype.StickyComponent;
import net.stickycode.stereotype.StickyPlugin;

public class StickySpringBootstrap {

  private GenericApplicationContext context;

  public StickySpringBootstrap(GenericApplicationContext context) {
    this.context = context;
    scan("net.stickycode");
  }

  public StickySpringBootstrap(String... paths) {
    this(new GenericApplicationContext());
    if (paths != null && paths.length > 0)
      scan(paths);
  }

  public void scan(String... paths) {
    ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(context, false);
    scanner.setScopeMetadataResolver(new StickyScopeMetadataResolver());
    scanner.addIncludeFilter(new AnnotationTypeFilter(StickyComponent.class));
    scanner.addIncludeFilter(new AnnotationTypeFilter(StickyPlugin.class));
    scanner.addIncludeFilter(new AnnotationTypeFilter(Component.class));
    scanner.scan(paths);
  }

  public AutowireCapableBeanFactory getAutowirer() {
    if (!context.isActive())
      context.refresh();

    return context.getAutowireCapableBeanFactory();
  }

}
