import React, { Component } from 'react';
import { ListGroup, ListGroupItem, Badge } from 'reactstrap';

class DataFileList extends Component {

    dateFormat = require('dateformat');

	datafiles(data) {
		if (data != null) {
			return data.map((node, index) => {
				return (
					<ListGroupItem key={node.id}>
						<ListGroup>
							<ListGroupItem>{node.s3Path} <Badge pill href={'http://download.alliancegenome.org/' + node.s3Path}>Download</Badge></ListGroupItem>
							<ListGroupItem>Uploaded: {this.dateFormat(node.uploadDate)}</ListGroupItem>
							<ListGroupItem>Schema Version: {node.schemaVersion.schema}</ListGroupItem>
							<ListGroupItem>Data Type: {node.dataType.name}</ListGroupItem>
							<ListGroupItem>Data SubType: {node.dataSubType.name}</ListGroupItem>
							<ListGroupItem hidden>{JSON.stringify(node)}</ListGroupItem>
						</ListGroup>
					</ListGroupItem>
				)
			})
		}
	}

	render() {
		return <ListGroup>{this.datafiles(this.props.data)}</ListGroup>
	}
}

export default DataFileList;