package net.stickycode.bootstrap.spring3;

import org.junit.Before;

import net.stickycode.bootstrap.tck.plugin.AbstractPluggableTest;

public class PluggableTest
    extends AbstractPluggableTest {

  @Before
  public void setup() {
    StickySpringBootstrap bootstrap = new StickySpringBootstrap(getClass().getPackage().getName());
    bootstrap.getAutowirer().autowireBean(this);
  }


}
