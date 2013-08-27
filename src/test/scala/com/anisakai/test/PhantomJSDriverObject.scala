package com.anisakai

import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.phantomjs.PhantomJSDriver

/**
 * Created with IntelliJ IDEA.
 * User: jbush
 * Date: 8/26/13
 * Time: 7:02 PM
 * To change this template use File | Settings | File Templates.
 */
object PhantomJSDriverObject {
  def apply(capabilities: DesiredCapabilities) = new PhantomJSDriver(capabilities)
}
