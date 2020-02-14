
package org.alliancegenome.agr_submission.views;

public class View {
	
	public class Default { }
	public class API extends Default { }

	public class DataFileView extends API { }
	public class DataFileCreate extends DataFileView { }
	public class DataFileRead extends DataFileView { }
	public class DataFileUpdate extends DataFileView { }
	public class DataFileDelete extends DataFileView { }
	
	public class DataTypeView extends API { }
	public class DataTypeCreate extends DataTypeView { }
	public class DataTypeRead extends DataTypeView { }
	public class DataTypeUpdate extends DataTypeView { }
	public class DataTypeDelete extends DataTypeView { }
	
	public class DataSubTypeView extends API { }
	public class DataSubTypeCreate extends DataSubTypeView { }
	public class DataSubTypeRead extends DataSubTypeView { }
	public class DataSubTypeUpdate extends DataSubTypeView { }
	public class DataSubTypeDelete extends DataSubTypeView { }
	
	public class ReleaseVersionView extends API { }
	public class ReleaseVersionCreate extends ReleaseVersionView { }
	public class ReleaseVersionRead extends ReleaseVersionView { }
	public class ReleaseVersionUpdate extends ReleaseVersionView { }
	public class ReleaseVersionDelete extends ReleaseVersionView { }
	
	public class SchemaVersionView extends API { }
	public class SchemaVersionCreate extends SchemaVersionView { }
	public class SchemaVersionRead extends SchemaVersionView { }
	public class SchemaVersionUpdate extends SchemaVersionView { }
	public class SchemaVersionDelete extends SchemaVersionView { }
	
	public class SnapShotView extends API { }
	public class SnapShotCreate extends SnapShotView { }
	public class SnapShotRead extends SnapShotView { }
	public class SnapShotUpdate extends SnapShotView { }
	public class SnapShotDelete extends SnapShotView { }
	
	public class SchemaFileView extends API { }
	public class SchemaFileCreate extends SchemaFileView { }
	public class SchemaFileRead extends SchemaFileView { }
	public class SchemaFileUpdate extends SchemaFileView { }
	public class SchemaFileDelete extends SchemaFileView { }
	
	public class UserView extends API { }
	public class UserCreate extends UserView { }
	public class UserRead extends UserView { }
	public class UserUpdate extends UserView { }
	public class UserDelete extends UserView { }
	
	
}
