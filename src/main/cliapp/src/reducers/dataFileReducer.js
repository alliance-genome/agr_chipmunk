import {
	LOAD_DATAFILES_BEGIN,
	LOAD_DATAFILES_SUCCESS,
	LOAD_DATAFILES_FAILURE
} from '../actions/actionTypes';

export default function dataTypeReducer(state = { dataFiles: [] }, action) {
	//console.log("Action: ", action.type, action.payload);
	switch (action.type) {
		case LOAD_DATAFILES_BEGIN:
			//console.log("LOAD_DATATYPES_BEGIN: ");
			return state;
		case LOAD_DATAFILES_FAILURE:
			//console.log("LOAD_DATATYPES_FAILURE: ");
			return state;

		case LOAD_DATAFILES_SUCCESS:
			return {
				...state,
				dataFiles: action.payload
			};
		default:
			return state;
	}
}