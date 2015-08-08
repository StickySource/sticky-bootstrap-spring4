package net.stickycode.bootstrap.spring3;

import org.junit.Before;

public class ComponentTest
    extends net.stickycode.bootstrap.tck.component.AbstractComponentTest {

  @Before
  public void setup() {
    StickySpringBootstrap bootstrap = new StickySpringBootstrap();
    bootstrap.getAutowirer().autowireBean(this);
  }
}
