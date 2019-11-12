package com.hello.fresh.rest.automation.framework.conf;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.io.File;

public enum ConfigFeeder {

  INSTANCE;

  public String env = System.getProperty("env");
  public String configName = System.getProperty("config", env + ".conf");
  public File configFile = new File(configName);
  public Config resourceConfig = ConfigFactory.load(String.format("conf/%s", configName));
  public Config customConfig = ConfigFactory.parseFile(configFile).withFallback(resourceConfig);
  public Config defaultConfig = ConfigFactory.load("conf/default");
  public Config baseConfig = ConfigFactory.load(String.format("conf/%s", env));
  public Config config = customConfig.withFallback(baseConfig).withFallback(defaultConfig).resolve();

}
