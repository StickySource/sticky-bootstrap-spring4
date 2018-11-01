package net.stickycode.bootstrap.spring4;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.GenericApplicationContext;

import net.stickycode.bootstrap.StickyBootstrap;

public class BeanFactoryPostProcessorTest {

  public class Example {

    private String value;

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }

  }

  @Inject
  private Example example;

  @Test
  public void empty() {
    StickyBootstrap crank = StickyBootstrap.crank();
    GenericApplicationContext context = (GenericApplicationContext) crank.getImplementation();
    assertThat(context.getBeanDefinitionCount()).isEqualTo(1); // component container
  }

  @Test
  public void checkBeanFactoryPostProcessors() {
    StickyBootstrap crank = StickyBootstrap.crank();
    GenericApplicationContext context = (GenericApplicationContext) crank.getImplementation();
    assertThat(context.getBeanDefinitionCount()).isEqualTo(1); // component container
    context.addBeanFactoryPostProcessor(new BeanFactoryPostProcessor() {

      @Override
      public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
          throws BeansException {
        assertThat(beanFactory.getBeanNamesForType(Example.class).length).isGreaterThan(0);
      }
    });
    crank.registerSingleton("bob", new Example(), Example.class);
    crank.inject(this);
  }
}
