import React from 'react';
import Collapsible from 'react-collapsible';
import { ListGroupItem, ListGroup, Badge } from 'reactstrap';

var dateFormat = require('dateformat');

const RenderDataFile = ({dataFile}) => (

	<div>
	{ dataFile != null && 
		<Collapsible trigger={ dateFormat(dataFile.uploadDate, "yyyy-mm-dd HH:MM:ss") + ": " + dataFile.dataType.name + "/" + dataFile.dataSubType.name }>
			<ListGroupItem key={dataFile.id}>
				<ListGroup>
					<ListGroupItem>{ dataFile.s3Path } <Badge pill href={'http://download.alliancegenome.org/' + dataFile.s3Path }>Download</Badge></ListGroupItem>
					<ListGroupItem>Uploaded: { dateFormat(dataFile.uploadDate) }</ListGroupItem>
					<ListGroupItem>Data Type: {dataFile.dataType.name}</ListGroupItem>
					<ListGroupItem>Data SubType: {dataFile.dataSubType.name}</ListGroupItem>
					<ListGroupItem hidden>{JSON.stringify(dataFile)}</ListGroupItem>
				</ListGroup>
			</ListGroupItem>
		</Collapsible>
	}
	</div>
);

export default RenderDataFile;