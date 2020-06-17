package com.coremedia.blueprint.contenthub.adapter.navigation.columns;

import com.coremedia.blueprint.contenthub.adapter.navigation.entities.BaseNavigationEntity;
import com.coremedia.contenthub.api.ContentHubObject;
import com.coremedia.contenthub.api.Folder;
import com.coremedia.contenthub.api.column.Column;
import com.coremedia.contenthub.api.column.ColumnValue;
import com.coremedia.contenthub.api.column.DefaultColumnProvider;

import java.util.ArrayList;
import java.util.List;

public class CoremediaNavigationColumnProvider extends DefaultColumnProvider {
  private static final String DATA_INDEX_LAST_MODIFIED = "lastModified";

  @Override
  public List<Column> getColumns(Folder folder) {
    List<Column> columns = new ArrayList<>(super.getColumns(folder));
    columns.add(new Column(DATA_INDEX_LAST_MODIFIED, DATA_INDEX_LAST_MODIFIED, 100, -1));
    return columns;
  }

  @Override
  public List<ColumnValue> getColumnValues(ContentHubObject hubObject) {
    BaseNavigationEntity item = (BaseNavigationEntity) hubObject;
    ColumnValue lastModified = new ColumnValue(DATA_INDEX_LAST_MODIFIED, item.getModificationDate(), "", "");
    List<ColumnValue> values = new ArrayList<>(super.getColumnValues(hubObject));
    values.add(lastModified);
    return values;
  }
}
