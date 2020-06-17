package com.coremedia.blueprint.contenthub.adapter.navigation.adapter;

import com.coremedia.blueprint.contenthub.adapter.navigation.cmnavigation.CoremediaNavigationSettings;
import com.coremedia.cap.content.ContentRepository;
import com.coremedia.cap.multisite.SitesService;
import com.coremedia.contenthub.api.ContentHubAdapter;
import com.coremedia.contenthub.api.ContentHubAdapterFactory;

import javax.inject.Inject;

public class CoremediaNavigationContentHubAdapterFactory implements ContentHubAdapterFactory<CoremediaNavigationSettings> {

  private static final String COREMEDIA_NAVIGATION_ID = "coremedia-navigation";
  private ContentRepository repository;
  private SitesService sitesService;

  @Override
  public String getId() {
    return COREMEDIA_NAVIGATION_ID;
  }

  @Override
  public ContentHubAdapter createAdapter(CoremediaNavigationSettings settings, String connectionID) {
    return new CoremediaNavigationContentHubAdapter(settings, sitesService, repository, connectionID);
  }

  @Inject
  public void setContentRepository(ContentRepository repository) {
    this.repository = repository;
  }

  @Inject
  public void setSitesService(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") SitesService service) {
    this.sitesService = service;
  }

}
