import React from 'react';
import { ListGroup } from 'reactstrap';
import RenderSnapshot from './RenderSnapshot';

const RenderSnapshots = ({snapshots}) => (
	<div>
		Snapshots:
		{ snapshots != null &&
			<ListGroup>
				{ snapshots.map((snapshot, index) => {
					return (
						<RenderSnapshot key={ snapshot.id } snapshot={ snapshot } />
					)
				})}
			</ListGroup>
		}
	</div>
);

export default RenderSnapshots;

