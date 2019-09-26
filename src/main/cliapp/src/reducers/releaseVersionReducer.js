import {
	LOAD_RELEASEVERSIONS_BEGIN,
	LOAD_RELEASEVERSIONS_SUCCESS,
	LOAD_RELEASEVERSIONS_FAILURE,
	LOAD_RELEASEVERSION_BEGIN,
	LOAD_RELEASEVERSION_SUCCESS,
	LOAD_RELEASEVERSION_FAILURE,
	LOAD_SNAPSHOTS_SUCCESS
} from '../actions/actionTypes';

export default function releaseVersionReducer(state = { releaseVersions: [], releaseVersion: {}, snapshots: [] }, action) {
	//console.log("Action: ", action.type, action.payload);
	switch (action.type) {
		case LOAD_RELEASEVERSIONS_BEGIN:
			//console.log("LOAD_RELEASEVERSIONS_BEGIN: ");
			return state;
		case LOAD_RELEASEVERSIONS_FAILURE:
			//console.log("LOAD_RELEASEVERSIONS_FAILURE: ");
			return state;
		case LOAD_RELEASEVERSION_BEGIN:
			//console.log("LOAD_RELEASEVERSION_BEGIN: ");
			return state;
		case LOAD_RELEASEVERSION_FAILURE:
			//console.log("LOAD_RELEASEVERSION_FAILURE: ");
			return state;
		case LOAD_RELEASEVERSIONS_SUCCESS:
			return {
				...state,
				releaseVersions: action.payload
			};
		case LOAD_RELEASEVERSION_SUCCESS:
			return {
				...state,
				releaseVersion: action.payload
			};
		case LOAD_SNAPSHOTS_SUCCESS:
			return {
				...state,
				snapshots: action.payload
			};

		default:
			return state;
	}
}
