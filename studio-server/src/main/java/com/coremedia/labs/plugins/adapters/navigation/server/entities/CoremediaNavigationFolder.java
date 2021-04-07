package com.coremedia.labs.plugins.adapters.navigation.server.entities;

import com.coremedia.cap.content.Content;
import com.coremedia.contenthub.api.Folder;
import edu.umd.cs.findbugs.annotations.NonNull;

public class CoremediaNavigationFolder extends BaseNavigationEntity implements Folder {
  public CoremediaNavigationFolder(@NonNull String cId, @NonNull Content folder) {
    super(folder, cId);
  }
}
