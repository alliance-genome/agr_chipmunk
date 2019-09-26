import React from 'react';
import { Card, CardBody, CardTitle, CardSubtitle } from 'reactstrap';
import RenderSnapshots from './RenderSnapshots';

var dateFormat = require('dateformat');

const RenderReleaseVersion = ({releaseVersion, snapshots}) => (
	<div>
		{ releaseVersion != null &&
		<Card>
			<CardBody>
				<CardTitle>ID: { releaseVersion.id }</CardTitle>
				<CardSubtitle>
					Release: { releaseVersion.releaseVersion }<br/>
					Release Date: { dateFormat(releaseVersion.releaseDate) }
				</CardSubtitle>
			</CardBody>
			<CardBody>
				<RenderSnapshots snapshots={snapshots} />
			</CardBody>
		</Card>
		}
	</div>

);

export default RenderReleaseVersion;