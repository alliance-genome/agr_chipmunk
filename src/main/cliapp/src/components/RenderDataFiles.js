import React from 'react';
import { ListGroup } from 'reactstrap';
import RenderDataFile from './RenderDataFile';

const RenderDataFiles = ({dataFiles}) => (
	<div>
		{ dataFiles != null &&
			<ListGroup>
				{ dataFiles.map((dataFile, index) => {
					return (
						<RenderDataFile key={ dataFile.id } dataFile={ dataFile } />
					)
				})}
			</ListGroup>
		}
	</div>
);

export default RenderDataFiles;

