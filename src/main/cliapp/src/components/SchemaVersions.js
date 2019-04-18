import React, { Component, Fragment } from 'react';
import { NavLink as RRNavLink } from 'react-router-dom';
import { connect } from 'react-redux';

import { CardBody, Card, ListGroup, ListGroupItem, NavLink } from 'reactstrap';

class SchemaVersions extends Component {

	render() {
		return (
			<Fragment>
				{this.props.schemaVersions.length > 0 && 
					<Card>
						<CardBody>
							<ListGroup>
								{this.props.schemaVersions.map((schemaversion, index) => {
									return (<ListGroupItem className="text-nowrap" key={schemaversion.id}>
										<NavLink to={"/admin/schemaversion/" + schemaversion.id} tag={RRNavLink}>
											{schemaversion.schema}
										</NavLink>
									</ListGroupItem>)
								})}
							</ListGroup>
						</CardBody>
					</Card>
				}
			</Fragment>
		);
	}

}

export default connect()(SchemaVersions);