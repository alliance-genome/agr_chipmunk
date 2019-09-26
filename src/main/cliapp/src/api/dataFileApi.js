class DataFileApi {

	static getDataFiles(dataType, dataSubType) {
		return fetch('/api/datafile/by/' + dataType + "/" + dataSubType).then(response => {
			return response.json();
		}).catch(error => {
			return error;
		})
	}
}

export default DataFileApi;
