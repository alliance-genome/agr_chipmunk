import {
	LOAD_SCHEMAVERSIONS_BEGIN,
	LOAD_SCHEMAVERSIONS_SUCCESS,
	LOAD_SCHEMAVERSIONS_FAILURE,
	LOAD_SCHEMAVERSION_BEGIN,
	LOAD_SCHEMAVERSION_SUCCESS,
	LOAD_SCHEMAVERSION_FAILURE
} from './actionTypes';
import SchemaVersionApi from '../api/schemaVersionApi';

export function loadSchemaVersions() {
	return (dispatch) => {
		dispatch(loadSchemaVersionsBegin());
		SchemaVersionApi.getAll().then(schemaVersions => {
			dispatch(loadSchemaVersionsSuccess(schemaVersions));
		}).catch(error => {
			dispatch(loadSchemaVersionsFailure(error));
		});
	};
}

export const loadSchemaVersion = (id) => {
	return function (dispatch) {
		dispatch(loadSchemaVersionBegin());
		SchemaVersionApi.getSchemaVersion(id).then(schemaVersion => {
			dispatch(loadSchemaVersionSuccess(schemaVersion));
		}).catch(error => {
			dispatch(loadSchemaVersionFailure(error));
		});
	};
}

export const loadSchemaVersionsBegin = () => ({
	type: LOAD_SCHEMAVERSIONS_BEGIN
});

export const loadSchemaVersionsSuccess = payload => ({
	type: LOAD_SCHEMAVERSIONS_SUCCESS,
	payload 
});

export const loadSchemaVersionsFailure = error => ({
	type: LOAD_SCHEMAVERSIONS_FAILURE,
	payload: { error }
});

export const loadSchemaVersionBegin = () => ({
	type: LOAD_SCHEMAVERSION_BEGIN
});

export const loadSchemaVersionSuccess = payload => ({
	type: LOAD_SCHEMAVERSION_SUCCESS,
	payload
});

export const loadSchemaVersionFailure = error => ({
	type: LOAD_SCHEMAVERSION_FAILURE,
	payload: { error }
});
