package org.queeg.util.spring;

import java.lang.reflect.Method;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class SpringThreadBean implements InitializingBean, DisposableBean {
  private ThreadGroup group = null;

  private Runnable target;

  private String name = null;

  private long stackSize = 0;

  private String shutdownMethod = null;

  private Method internalShutdownMethod = null;

  private Thread thread;

  @Override
  public void afterPropertiesSet() throws Exception {
    if (target == null) {
      throw new IllegalArgumentException("Property \"target\" must be set");
    }

    thread = new Thread(group, target, name, stackSize);
    thread.start();

    if (shutdownMethod != null) {
      Method[] methods = target.getClass().getMethods();
      for (Method method : methods) {
        if (shutdownMethod.equals(method.getName()) && method.getParameterTypes().length == 0) {
          internalShutdownMethod = method;
          continue;
        }
      }
      
      if (internalShutdownMethod == null) {
        throw new IllegalArgumentException("shutdownMethod must be a method on target and take no arguments");
      }
    }
  }

  @Override
  public void destroy() throws Exception {
    if (internalShutdownMethod != null) {
      internalShutdownMethod.invoke(target);
    }
  }

  public ThreadGroup getGroup() {
    return group;
  }

  public void setGroup(ThreadGroup group) {
    this.group = group;
  }

  public Runnable getTarget() {
    return target;
  }

  public void setTarget(Runnable target) {
    this.target = target;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getStackSize() {
    return stackSize;
  }

  public void setStackSize(long stackSize) {
    this.stackSize = stackSize;
  }

  public String getShutdownMethod() {
    return shutdownMethod;
  }

  public void setShutdownMethod(String shutdownMethod) {
    this.shutdownMethod = shutdownMethod;
  }
}
