import {
	LOAD_DATASUBTYPES_BEGIN,
	LOAD_DATASUBTYPES_SUCCESS,
	LOAD_DATASUBTYPES_FAILURE
} from './actionTypes';

import dataSubTypeApi from '../api/dataSubTypeApi';

export function loadDataSubTypes() {
	return (dispatch) => {
		dispatch(loadDataSubTypesBegin());
		dataSubTypeApi.getAll().then(dataSubTypes => {
			dispatch(loadDataSubTypesSuccess(dataSubTypes));
		}).catch(error => {
			dispatch(loadDataSubTypesFailure(error));
		});
	};
}

export const loadDataSubTypesBegin = () => ({
	type: LOAD_DATASUBTYPES_BEGIN
});

export const loadDataSubTypesSuccess = payload => ({
	type: LOAD_DATASUBTYPES_SUCCESS,
	payload 
});

export const loadDataSubTypesFailure = error => ({
	type: LOAD_DATASUBTYPES_FAILURE,
	payload: { error }
});