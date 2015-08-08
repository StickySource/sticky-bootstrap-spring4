package net.stickycode.bootstrap.spring3;

import org.junit.Before;

import net.stickycode.bootstrap.tck.configured.AbstractConfiguredTest;

public class ConfiguredTest
    extends AbstractConfiguredTest {

  @Before
  public void setup() {
    StickySpringBootstrap bootstrap = new StickySpringBootstrap(getClass().getPackage().getName());
    bootstrap.getAutowirer().autowireBean(this);
  }
}
