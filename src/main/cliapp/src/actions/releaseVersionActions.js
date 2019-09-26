import {
	LOAD_RELEASEVERSIONS_BEGIN,
	LOAD_RELEASEVERSIONS_SUCCESS,
	LOAD_RELEASEVERSIONS_FAILURE,
	LOAD_RELEASEVERSION_BEGIN,
	LOAD_RELEASEVERSION_SUCCESS,
	LOAD_RELEASEVERSION_FAILURE,
	LOAD_SNAPSHOTS_BEGIN,
	LOAD_SNAPSHOTS_SUCCESS,
	LOAD_SNAPSHOTS_FAILURE
} from './actionTypes';

import ReleaseVersionApi from '../api/releaseVersionApi';

export function loadReleaseVersions() {
	return (dispatch) => {
		dispatch(loadReleaseVersionsBegin());
		ReleaseVersionApi.getAll().then(releaseVersions => {
			dispatch(loadReleaseVersionsSuccess(releaseVersions));
		}).catch(error => {
			dispatch(loadReleaseVersionsFailure(error));
		});
	};
}

export const loadReleaseVersion = (id) => {
	return function (dispatch) {
		dispatch(loadReleaseVersionBegin());
		ReleaseVersionApi.getReleaseVersion(id).then(releaseVersion => {
			dispatch(loadReleaseVersionSuccess(releaseVersion));
		}).catch(error => {
			dispatch(loadReleaseVersionFailure(error));
		});
	};
}

export const loadReleaseVersionSnapshots = (id) => {
	return function (dispatch) {
		dispatch(loadSnapshotsBegin());
		ReleaseVersionApi.getReleaseVersionSnapshots(id).then(snapshots => {
			dispatch(loadSnapshotsSuccess(snapshots));
		}).catch(error => {
			dispatch(loadSnapshotsFailure(error));
		});
	};
}

export const loadReleaseVersionsBegin = () => ({ type: LOAD_RELEASEVERSIONS_BEGIN });
export const loadReleaseVersionsSuccess = payload => ({ type: LOAD_RELEASEVERSIONS_SUCCESS, payload });
export const loadReleaseVersionsFailure = error => ({ type: LOAD_RELEASEVERSIONS_FAILURE, payload: { error } });

export const loadReleaseVersionBegin = () => ({ type: LOAD_RELEASEVERSION_BEGIN });
export const loadReleaseVersionSuccess = payload => ({ type: LOAD_RELEASEVERSION_SUCCESS, payload });
export const loadReleaseVersionFailure = error => ({ type: LOAD_RELEASEVERSION_FAILURE, payload: { error } });

export const loadSnapshotsBegin = () => ({ type: LOAD_SNAPSHOTS_BEGIN });
export const loadSnapshotsSuccess = payload => ({ type: LOAD_SNAPSHOTS_SUCCESS, payload });
export const loadSnapshotsFailure = error => ({ type: LOAD_SNAPSHOTS_FAILURE, payload: { error } });
