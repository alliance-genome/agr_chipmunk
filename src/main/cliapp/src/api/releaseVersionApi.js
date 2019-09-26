
class ReleaseVersionApi {

	static getAll() {
		return fetch('/api/releaseversion/all').then(response => {
			//console.log("Data Types API Running");
			return response.json();
		}).catch(error => {
			return error;
		});
	}

	static getReleaseVersion(releaseVersion) {
		return fetch('/api/releaseversion/' + releaseVersion).then(response => {
			//console.log("Data Type API Running", releaseVersion);
			return response.json();
		}).catch(error => {
			return error;
		})
	}

	static getReleaseVersionSnapshots(releaseVersion) {
		return fetch('/api/releaseversion/' + releaseVersion + "/snapshots").then(response => {
			//console.log("Data Type API Running", releaseVersion);
			return response.json();
		}).catch(error => {
			return error;
		})
	}
}

export default ReleaseVersionApi;
