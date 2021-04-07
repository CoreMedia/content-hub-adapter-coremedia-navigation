package com.coremedia.labs.plugins.adapters.navigation.server.entities;

import com.coremedia.cap.content.Content;
import com.coremedia.contenthub.api.Item;

public class CoremediaNavigationItem extends BaseNavigationEntity implements Item {

  public CoremediaNavigationItem(String connectionId, Content content) {
    super(content, connectionId);
  }

  @Override
  public String getCoreMediaContentType() {
    return getDelegate().getType().getName();
  }
}
