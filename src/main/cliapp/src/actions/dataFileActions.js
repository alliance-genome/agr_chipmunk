import {
	LOAD_DATAFILES_BEGIN,
	LOAD_DATAFILES_SUCCESS,
	LOAD_DATAFILES_FAILURE
} from './actionTypes';

import dataFileApi from '../api/dataFileApi';

export const loadDataFiles = (dataType, dataSubType) => {
	return function (dispatch) {
		dispatch(loadDataFilesBegin());
		dataFileApi.getDataFiles(dataType, dataSubType).then(dataFile => {
			dispatch(loadDataFilesSuccess(dataFile));
		}).catch(error => {
			dispatch(loadDataFilesFailure(error));
		});
	};
}

export const loadDataFilesBegin = () => ({
	type: LOAD_DATAFILES_BEGIN
});

export const loadDataFilesSuccess = payload => ({
	type: LOAD_DATAFILES_SUCCESS,
	payload 
});

export const loadDataFilesFailure = error => ({
	type: LOAD_DATAFILES_FAILURE,
	payload: { error }
});