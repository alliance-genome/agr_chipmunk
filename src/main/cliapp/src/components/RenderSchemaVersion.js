import React from 'react';
import { Card, CardBody, CardTitle, CardSubtitle } from 'reactstrap';
import RenderDataFiles from './RenderDataFiles';

const RenderSchemaVersion = ({schemaVersion}) => (
	<div>
		{ schemaVersion != null &&
		<Card>
			<CardBody>
				<CardTitle>ID: { schemaVersion.id }</CardTitle>
				<CardSubtitle>Schema: { schemaVersion.schema }</CardSubtitle>
			</CardBody>
			<CardBody>
				Current data files uploaded with this Schema Version:
				<RenderDataFiles dataFiles={ schemaVersion.dataFiles } />
			</CardBody>
		</Card>
		}
	</div>

);

export default RenderSchemaVersion;