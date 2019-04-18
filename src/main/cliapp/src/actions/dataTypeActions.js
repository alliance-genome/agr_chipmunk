import {
	LOAD_DATATYPES_BEGIN,
	LOAD_DATATYPES_SUCCESS,
	LOAD_DATATYPES_FAILURE,
	LOAD_DATATYPE_BEGIN,
	LOAD_DATATYPE_SUCCESS,
	LOAD_DATATYPE_FAILURE
} from './actionTypes';
import dataTypeApi from '../api/dataTypeApi';

export function loadDataTypes() {
	return (dispatch) => {
		dispatch(loadDataTypesBegin());
		dataTypeApi.getAll().then(dataTypes => {
			dispatch(loadDataTypesSuccess(dataTypes));
		}).catch(error => {
			dispatch(loadDataTypesFailure(error));
		});
	};
}

export const loadDataType = (id) => {
	return function (dispatch) {
		dispatch(loadDataTypeBegin());
		dataTypeApi.getDataType(id).then(dataType => {
			dispatch(loadDataTypeSuccess(dataType));
		}).catch(error => {
			dispatch(loadDataTypeFailure(error));
		});
	};
}

export const loadDataTypesBegin = () => ({
	type: LOAD_DATATYPES_BEGIN
});

export const loadDataTypesSuccess = payload => ({
	type: LOAD_DATATYPES_SUCCESS,
	payload 
});

export const loadDataTypesFailure = error => ({
	type: LOAD_DATATYPES_FAILURE,
	payload: { error }
});

export const loadDataTypeBegin = () => ({
	type: LOAD_DATATYPE_BEGIN
});

export const loadDataTypeSuccess = payload => ({
	type: LOAD_DATATYPE_SUCCESS,
	payload
});

export const loadDataTypeFailure = error => ({
	type: LOAD_DATATYPE_FAILURE,
	payload: { error }
});
