package com.coremedia.labs.plugins.adapters.navigation.server.adapter;

import com.coremedia.cap.content.ContentRepository;
import com.coremedia.cap.multisite.SitesService;
import com.coremedia.contenthub.api.ContentHubAdapter;
import com.coremedia.contenthub.api.ContentHubAdapterFactory;
import com.coremedia.labs.plugins.adapters.navigation.server.CoremediaNavigationSettings;

public class CoremediaNavigationContentHubAdapterFactory implements ContentHubAdapterFactory<CoremediaNavigationSettings> {

  private static final String COREMEDIA_NAVIGATION_ID = "coremedia-navigation";
  private final ContentRepository repository;
  private final SitesService sitesService;

  public CoremediaNavigationContentHubAdapterFactory(ContentRepository repository, SitesService sitesService) {
    this.repository = repository;
    this.sitesService = sitesService;
  }

  @Override
  public String getId() {
    return COREMEDIA_NAVIGATION_ID;
  }

  @Override
  public ContentHubAdapter createAdapter(CoremediaNavigationSettings settings, String connectionID) {
    return new CoremediaNavigationContentHubAdapter(sitesService, repository, connectionID);
  }
}
