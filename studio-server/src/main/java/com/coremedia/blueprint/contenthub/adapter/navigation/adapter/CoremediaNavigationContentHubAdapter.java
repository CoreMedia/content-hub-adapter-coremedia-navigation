package com.coremedia.blueprint.contenthub.adapter.navigation.adapter;

import com.coremedia.blueprint.contenthub.adapter.navigation.cmnavigation.CoremediaNavigationSettings;
import com.coremedia.blueprint.contenthub.adapter.navigation.columns.CoremediaNavigationColumnProvider;
import com.coremedia.blueprint.contenthub.adapter.navigation.constants.CoremediaNavigationConstants;
import com.coremedia.blueprint.contenthub.adapter.navigation.entities.CoremediaNavigationFolder;
import com.coremedia.blueprint.contenthub.adapter.navigation.entities.CoremediaNavigationItem;
import com.coremedia.cap.content.Content;
import com.coremedia.cap.content.ContentRepository;
import com.coremedia.cap.multisite.Site;
import com.coremedia.cap.multisite.SitesService;
import com.coremedia.contenthub.api.ContentHubAdapter;
import com.coremedia.contenthub.api.ContentHubContext;
import com.coremedia.contenthub.api.ContentHubObject;
import com.coremedia.contenthub.api.ContentHubObjectId;
import com.coremedia.contenthub.api.ContentHubTransformer;
import com.coremedia.contenthub.api.Folder;
import com.coremedia.contenthub.api.GetChildrenResult;
import com.coremedia.contenthub.api.Item;
import com.coremedia.contenthub.api.column.ColumnProvider;
import com.coremedia.contenthub.api.exception.ContentHubException;
import com.coremedia.contenthub.api.pagination.PaginationRequest;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.stream.Collectors;

import static com.coremedia.blueprint.contenthub.adapter.navigation.constants.CoremediaNavigationConstants.NAVIGATION_PROPERTY;


public class CoremediaNavigationContentHubAdapter implements ContentHubAdapter {

  private static final Logger LOGGER = LoggerFactory.getLogger(CoremediaNavigationContentHubAdapter.class);


  private SitesService sitesService;
  private ContentRepository contentRepository;
  private String connectionId;
  private CoremediaNavigationColumnProvider columnProvider;

  CoremediaNavigationContentHubAdapter(@NonNull SitesService sitesService,
                                       @NonNull ContentRepository repository,
                                       @NonNull String connectionId) {
    this.sitesService = sitesService;
    this.contentRepository = repository;
    this.connectionId = connectionId;
    this.columnProvider = new CoremediaNavigationColumnProvider();
  }

  @Override
  public Folder getRootFolder(ContentHubContext contentHubContext) throws ContentHubException {
    Site site = findSite(contentHubContext).orElseThrow(() -> new ContentHubException("Site can not be empty"));
    Content siteRoot = site.getSiteRootDocument();
    LOGGER.debug("Creating new CoreMediaNavigation rootfolder for name " + site.getName() + " and id " + site.getId());
    if (siteRoot == null) {
      throw new RuntimeException("cannot find siteRoot for preferred Site");
    }
    return new CoremediaNavigationFolder(this.connectionId, siteRoot);
  }

  @Nullable
  @Override
  public Item getItem(ContentHubContext contentHubContext, ContentHubObjectId contentHubObjectId) throws ContentHubException {
    return getItemOrFolder(contentHubContext, contentHubObjectId).map(o -> new CoremediaNavigationItem(this.connectionId, o)).orElseThrow(() -> new IllegalArgumentException("Illegal id for item"));
  }

  @Nullable
  @Override
  public Folder getFolder(ContentHubContext contentHubContext, ContentHubObjectId contentHubObjectId) throws ContentHubException {
    return getItemOrFolder(contentHubContext, contentHubObjectId).map(o -> new CoremediaNavigationFolder(this.connectionId, o)).orElseThrow(() -> new IllegalArgumentException("Illegal id for folder"));
  }

  @Nullable
  @Override
  public Folder getParent(ContentHubContext contentHubContext, ContentHubObject contentHubObject) throws ContentHubException {
    Content current = contentRepository.getContent(contentHubObject.getId().getExternalId());
    Site site = sitesService.getContentSiteAspect(current).getSite();
    if (site == null || site.getSiteRootDocument() == null || site.getSiteRootDocument().equals(current)) {
      return null;
    }
    Content parentContent = current.getReferrerWithDescriptor(CoremediaNavigationConstants.CMCHANNEL_TYPE, NAVIGATION_PROPERTY);
    if (parentContent == null) {
      LOGGER.warn("found content with id " + current.getId() + " without any parent");
      return null;
    }
    return new CoremediaNavigationFolder(this.connectionId, parentContent);
  }

  @Override
  public ContentHubTransformer transformer() {
    return null;
  }

  @Override
  public GetChildrenResult getChildren(ContentHubContext contentHubContext, Folder folder, @Nullable PaginationRequest paginationRequest) {
    Content content = contentRepository.getContent(folder.getId().getExternalId());

    return new GetChildrenResult(content.getLinks(NAVIGATION_PROPERTY)
            .stream()
            .filter(c -> {
              String contentType = c.getType().getName();
              return contentType.equals(CoremediaNavigationConstants.CMCHANNEL_TYPE) ||
                      contentType.equals(CoremediaNavigationConstants.CMEXTERNAL_CHANNEL_TYPE) ||
                      contentType.equals(CoremediaNavigationConstants.CMEXTERNAL_PAGE_TYPE);
            })
            .map(c -> {
              if (c.getLinks(NAVIGATION_PROPERTY).isEmpty()) {
                return new CoremediaNavigationItem(this.connectionId, c);
              } else {
                return new CoremediaNavigationFolder(this.connectionId, c);
              }
            })
            .collect(Collectors.toList()));
  }

  private Optional<Content> getItemOrFolder(ContentHubContext contentHubContext, ContentHubObjectId contentHubObjectId) {
    Content content = contentRepository.getContent(contentHubObjectId.getExternalId());
    if (content == null) {
      return Optional.empty();
    }
    Site site = findSite(contentHubContext).orElse(null);
    if (site == null) {
      return Optional.empty();
    }
    return Optional.ofNullable(sitesService.isContentInSite(site, content) ? content : null);
  }

  @Override
  public ColumnProvider columnProvider() {
    return this.columnProvider;
  }

  private Optional<Site> findSite(final ContentHubContext contentHubContext) {
    return sitesService
            .getSites()
            .stream()
            .filter(s -> s.getName().equals(contentHubContext.getSiteName()) && s.getLocale().equals(contentHubContext.getSiteLocale()))
            .findFirst();
  }
}
