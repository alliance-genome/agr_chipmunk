
class DataSubTypeApi {

	static getAll() {
		return fetch('http://localhost:8080/api/datasubtype/all').then(response => {
			//console.log("Data Types API Running");
			return response.json();
		}).catch(error => {
			return error;
		});
	}
}

export default DataSubTypeApi;