import {
	LOAD_DATASUBTYPES_BEGIN,
	LOAD_DATASUBTYPES_SUCCESS,
	LOAD_DATASUBTYPES_FAILURE
} from '../actions/actionTypes';

export default function dataSubTypeReducer(state = { dataSubTypes: [] }, action) {
	//console.log("Action: ", action.type, action.payload);
	switch (action.type) {
		case LOAD_DATASUBTYPES_BEGIN:
			//console.log("LOAD_DATATYPES_BEGIN: ");
			return state;
		case LOAD_DATASUBTYPES_FAILURE:
			//console.log("LOAD_DATATYPES_FAILURE: ");
			return state;
		case LOAD_DATASUBTYPES_SUCCESS:
			return {
				...state,
				dataSubTypes: action.payload
			};
		default:
			return state;
	}
}
