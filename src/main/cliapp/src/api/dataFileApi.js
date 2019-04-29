class DataFileApi {

	static getDataFiles(dataType, dataSubType) {
		return fetch('/api/datafile/' + dataType + "/" + dataSubType).then(response => {
			return response.json();
		}).catch(error => {
			return error;
		})
	}
}

export default DataFileApi;
