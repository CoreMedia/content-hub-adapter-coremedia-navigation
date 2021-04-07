package com.coremedia.labs.plugins.adapters.navigation.server.entities;

import com.coremedia.cap.content.Content;
import com.coremedia.contenthub.api.ContentHubObject;
import com.coremedia.contenthub.api.ContentHubObjectId;
import com.coremedia.contenthub.api.ContentHubType;
import com.coremedia.contenthub.api.preview.DetailsElement;
import com.coremedia.contenthub.api.preview.DetailsSection;
import com.coremedia.labs.plugins.adapters.navigation.server.constants.CoremediaNavigationConstants;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;

import java.util.Calendar;
import java.util.List;

public class BaseNavigationEntity implements ContentHubObject {
  private final Content delegate;
  private final String connectionId;

  BaseNavigationEntity(Content delegate, String connectionId) {
    this.delegate = delegate;
    this.connectionId = connectionId;

  }

  @Override
  public ContentHubObjectId getId() {
    return new ContentHubObjectId(getConnectionId(), getDelegate().getId());
  }

  @Override
  public String getName() {
    return getDelegate().getName();
  }

  @Nullable
  @Override
  public String getDescription() {
    return getDelegate().getPath();
  }

  @Override
  public String getDisplayName() {
    return getDelegate().getName();
  }

  @Override
  public ContentHubType getContentHubType() {
    return new ContentHubType(getDelegate().getType().getName());
  }

  public Calendar getModificationDate() {
    return getDelegate().getModificationDate();
  }

  @NonNull
  @Override
  public List<DetailsSection> getDetails() {
    DetailsSection section = new DetailsSection(CoremediaNavigationConstants.CONTENT_INFO,
            List.of(new DetailsElement<>(CoremediaNavigationConstants.PATH, getDelegate().getPath())));
    return List.of(section);
  }


  Content getDelegate() {
    return delegate;
  }

  String getConnectionId() {
    return connectionId;
  }
}
