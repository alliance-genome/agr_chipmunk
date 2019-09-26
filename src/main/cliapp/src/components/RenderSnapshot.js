import React from 'react';
import Collapsible from 'react-collapsible';
import { ListGroupItem, ListGroup } from 'reactstrap';
import RenderDataFiles from './RenderDataFiles';

var dateFormat = require('dateformat');

const RenderSnapshot = ({snapshot}) => (
	<div>

	{ snapshot != null && 
		<Collapsible trigger={ dateFormat(snapshot.snapShotDate) }>
			<ListGroup>
				<ListGroupItem>Snapshot Date: { dateFormat(snapshot.snapShotDate) }</ListGroupItem>
				<ListGroupItem><RenderDataFiles dataFiles={ snapshot.dataFiles } /></ListGroupItem>
			</ListGroup>
		</Collapsible>
	}
	</div>
);

export default RenderSnapshot;