import {
	LOAD_SCHEMAVERSIONS_BEGIN,
	LOAD_SCHEMAVERSIONS_SUCCESS,
	LOAD_SCHEMAVERSIONS_FAILURE,
	LOAD_SCHEMAVERSION_BEGIN,
	LOAD_SCHEMAVERSION_SUCCESS,
	LOAD_SCHEMAVERSION_FAILURE
} from '../actions/actionTypes';

export default function schemaVersionReducer(state = { schemaVersions: [], schemaVersion: {} }, action) {
	//console.log("Action: ", action.type, action.payload);
	switch (action.type) {
		case LOAD_SCHEMAVERSIONS_BEGIN:
			//console.log("LOAD_SCHEMAVERSIONS_BEGIN: ");
			return state;
		case LOAD_SCHEMAVERSIONS_FAILURE:
			//console.log("LOAD_SCHEMAVERSIONS_FAILURE: ");
			return state;
		case LOAD_SCHEMAVERSION_BEGIN:
			//console.log("LOAD_SCHEMAVERSION_BEGIN: ");
			return state;
		case LOAD_SCHEMAVERSION_FAILURE:
			//console.log("LOAD_SCHEMAVERSION_FAILURE: ");
			return state;
		case LOAD_SCHEMAVERSIONS_SUCCESS:
			return {
				...state,
				schemaVersions: action.payload
			};
		case LOAD_SCHEMAVERSION_SUCCESS:
			return {
				...state,
				schemaVersion: action.payload
			};
		default:
			return state;
	}
}
