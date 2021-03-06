package com.coremedia.labs.plugins.adapters.navigation.server;

import com.coremedia.cap.common.CapConnection;
import com.coremedia.cap.multisite.SitesService;
import com.coremedia.cms.common.plugins.beans_for_plugins.CommonBeansForPluginsConfiguration;
import com.coremedia.labs.plugins.adapters.navigation.server.adapter.CoremediaNavigationContentHubAdapterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Main configuration class for the Coremedia-navigation content-hub adapter configured in
 * the document META-INF/spring.factories.
 */
@Configuration
@Import({CommonBeansForPluginsConfiguration.class})
public class CoremediaNavigationConfiguration {

  @Bean
  CoremediaNavigationContentHubAdapterFactory coremediaNavigationContentHubAdapterFactory(CapConnection connection,
                                                                                          SitesService sitesService) {
    return new CoremediaNavigationContentHubAdapterFactory(connection.getContentRepository(), sitesService);
  }
}
