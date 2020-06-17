package com.coremedia.blueprint.contenthub.adapter.navigation.entities;

import com.coremedia.cap.content.Content;
import com.coremedia.contenthub.api.ContentHubObject;
import com.coremedia.contenthub.api.ContentHubObjectId;
import com.coremedia.contenthub.api.ContentHubType;
import com.coremedia.contenthub.api.preview.DetailsElement;
import com.coremedia.contenthub.api.preview.DetailsSection;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;

import java.util.Calendar;
import java.util.List;

import static com.coremedia.blueprint.contenthub.adapter.navigation.constants.CoremediaNavigationConstants.CONTENT_INFO;
import static com.coremedia.blueprint.contenthub.adapter.navigation.constants.CoremediaNavigationConstants.PATH;

public class BaseNavigationEntity implements ContentHubObject {
  private Content delegate;
  private String connectionId;

  public BaseNavigationEntity(Content delegate, String connectionId) {
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
    DetailsSection section = new DetailsSection(CONTENT_INFO,
            List.of(new DetailsElement<>(PATH, getDelegate().getPath())));
    return List.of(section);
  }


  public Content getDelegate() {
    return delegate;
  }

  public void setDelegate(Content delegate) {
    this.delegate = delegate;
  }

  public String getConnectionId() {
    return connectionId;
  }

  public void setConnectionId(String connectionId) {
    this.connectionId = connectionId;
  }


}
