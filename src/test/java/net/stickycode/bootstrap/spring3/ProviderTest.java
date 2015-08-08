package net.stickycode.bootstrap.spring3;

import org.junit.Before;

import net.stickycode.bootstrap.tck.provider.AbstractProviderTest;

public class ProviderTest
    extends AbstractProviderTest {

  @Before
  public void setup() {
    StickySpringBootstrap bootstrap = new StickySpringBootstrap();
    bootstrap.getAutowirer().autowireBean(this);
  }

}
