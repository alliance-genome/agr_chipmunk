
class DataTypeApi {

	static getAll() {
		return fetch('http://localhost:8080/api/datatype/all').then(response => {
			//console.log("Data Types API Running");
			return response.json();
		}).catch(error => {
			return error;
		});
	}

	static getDataType(dataType) {
		return fetch('http://localhost:8080/api/datatype/' + dataType).then(response => {
			//console.log("Data Type API Running", dataType);
			return response.json();
		}).catch(error => {
			return error;
		})
	}
}

export default DataTypeApi;