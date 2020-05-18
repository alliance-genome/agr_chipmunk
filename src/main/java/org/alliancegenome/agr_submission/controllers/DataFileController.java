package org.alliancegenome.agr_submission.controllers;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.alliancegenome.agr_submission.BaseController;
import org.alliancegenome.agr_submission.entities.DataFile;
import org.alliancegenome.agr_submission.interfaces.server.DataFileControllerInterface;
import org.alliancegenome.agr_submission.services.DataFileService;

@RequestScoped
public class DataFileController extends BaseController implements DataFileControllerInterface {

	@Inject DataFileService dataFileService;
	
	@Override
	public DataFile create(String releaseVersion, String dataType, String dataSubtype, DataFile entity) {
		if(entity != null) {
			return dataFileService.create(releaseVersion, dataType, dataSubtype, entity);
		} else {
			return null;
		}
	}

	@Override
	public DataFile get(String id) {
		return dataFileService.get(id);
	}

	@Override
	public DataFile update(DataFile entity) {
		return dataFileService.update(entity);
	}

	@Override
	public DataFile delete(Long id) {
		return dataFileService.delete(id);
	}
	
	@Override
	public DataFile changeDataType(String id, String dataType) {
		return dataFileService.changeDataType(id, dataType);
	}
	
	@Override
	public List<DataFile> assignDataFilesFromRelease1ToRelease2(String releaseVersion1, String releaseVersion2) {
		return dataFileService.assignDataFilesFromRelease1ToRelease2(releaseVersion1, releaseVersion2);
	}
	
	public List<DataFile> getDataFiles() {
		return dataFileService.getDataFiles();
	}

	@Override
	public List<DataFile> getDataTypeFiles(String dataType, Boolean latest) {
		return dataFileService.getDataTypeFiles(dataType, latest);
	}

	@Override
	public List<DataFile> getDataTypeSubTypeFiles(String dataType, String dataSubtype, Boolean latest) {
		return dataFileService.getDataTypeSubTypeFiles(dataType, dataSubtype, latest);
	}
	
	@Override
	public List<DataFile> getReleaseDataTypeSubTypeFiles(String releaseVersion, String dataType, String dataSubtype, Boolean latest) {
		return dataFileService.getReleaseDataTypeSubTypeFiles(releaseVersion, dataType, dataSubtype, latest);
	}

	@Override
	public List<DataFile> getDataFilesByRelease(String releaseVersion, Boolean latest) {
		return dataFileService.getDataFilesByRelease(releaseVersion, latest);
	}

	@Override
	public DataFile validateToggle(String id) {
		return dataFileService.validateToggle(id);
	}

	@Override
	public DataFile changeDataSubType(String id, String dataSubType) {
		return dataFileService.changeDataSubType(id, dataSubType);
	}

	@Override
	public DataFile changeDataTypeAndDataSubType(String id, String dataType, String dataSubType) {
		return dataFileService.changeDataTypeAndDataSubType(id, dataType, dataSubType);
	}

}
