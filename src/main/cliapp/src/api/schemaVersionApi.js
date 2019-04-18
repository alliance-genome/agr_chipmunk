
class SchemaVersionApi {

	static getAll() {
		return fetch('http://localhost:8080/api/schemaversion/all').then(response => {
			//console.log("Data Types API Running");
			return response.json();
		}).catch(error => {
			return error;
		});
	}

	static getSchemaVersion(schemaVersion) {
		return fetch('http://localhost:8080/api/schemaversion/' + schemaVersion).then(response => {
			//console.log("Data Type API Running", schemaVersion);
			return response.json();
		}).catch(error => {
			return error;
		})
	}
}

export default SchemaVersionApi;
