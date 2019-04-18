import {
	LOAD_DATATYPES_BEGIN,
	LOAD_DATATYPES_SUCCESS,
	LOAD_DATATYPES_FAILURE,
	LOAD_DATATYPE_BEGIN,
	LOAD_DATATYPE_SUCCESS,
	LOAD_DATATYPE_FAILURE
} from '../actions/actionTypes';

export default function dataTypeReducer(state = { dataTypes: [], dataType: {} }, action) {
	//console.log("Action: ", action.type, action.payload);
	switch (action.type) {
		case LOAD_DATATYPES_BEGIN:
			//console.log("LOAD_DATATYPES_BEGIN: ");
			return state;
		case LOAD_DATATYPES_FAILURE:
			//console.log("LOAD_DATATYPES_FAILURE: ");
			return state;
		case LOAD_DATATYPE_BEGIN:
			//console.log("LOAD_DATATYPE_BEGIN: ");
			return state;
		case LOAD_DATATYPE_FAILURE:
			//console.log("LOAD_DATATYPE_FAILURE: ");
			return state;
		case LOAD_DATATYPES_SUCCESS:
			return {
				...state,
				dataTypes: action.payload
			};
		case LOAD_DATATYPE_SUCCESS:
			return {
				...state,
				dataType: action.payload
			};
		default:
			return state;
	}
}