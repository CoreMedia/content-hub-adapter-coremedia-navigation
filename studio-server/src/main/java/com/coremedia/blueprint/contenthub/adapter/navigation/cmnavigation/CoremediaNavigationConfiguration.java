package com.coremedia.blueprint.contenthub.adapter.navigation.cmnavigation;

import com.coremedia.blueprint.contenthub.adapter.navigation.adapter.CoremediaNavigationContentHubAdapterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Main configuration class for the Coremedia-navigation content-hub adapter configured in
 * the document META-INF/spring.factories.
 */
@Configuration
public class CoremediaNavigationConfiguration {

  @Bean
  CoremediaNavigationContentHubAdapterFactory coremediaNavigationContentHubAdapterFactory() {
    return new CoremediaNavigationContentHubAdapterFactory();
  }
}
