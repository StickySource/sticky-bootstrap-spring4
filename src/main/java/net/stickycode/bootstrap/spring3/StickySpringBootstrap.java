package net.stickycode.bootstrap.spring3;

import net.stickycode.stereotype.StickyComponent;
import net.stickycode.stereotype.StickyPlugin;

import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

public class StickySpringBootstrap {

  private GenericApplicationContext context;

  public StickySpringBootstrap(GenericApplicationContext context) {
    this.context = context;
  }

  public void scan(String... paths) {
    ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(context, false);
    scanner.setScopeMetadataResolver(new StickyScopeMetadataResolver());
    scanner.addIncludeFilter(new AnnotationTypeFilter(StickyComponent.class));
    scanner.addIncludeFilter(new AnnotationTypeFilter(StickyPlugin.class));
    scanner.addIncludeFilter(new AnnotationTypeFilter(Component.class));
    scanner.scan(paths);
  }

}
