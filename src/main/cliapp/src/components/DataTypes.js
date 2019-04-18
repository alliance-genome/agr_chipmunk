import React, { Component, Fragment } from 'react';
import { NavLink as RRNavLink } from 'react-router-dom';
import { connect } from 'react-redux';
//import { loadDataTypes } from "../actions/dataTypeActions";

import { CardBody, Card, ListGroup, ListGroupItem, NavLink, Badge } from 'reactstrap';

class DataTypes extends Component {

	renderdatatypes() {
		return this.props.dataTypes.map((node, index) => {
			return (
				<ListGroupItem className="text-nowrap" key={node.id}>
					<NavLink to={"/admin/datatype/" + node.id} tag={RRNavLink}>
						<Badge>{node.name}</Badge>: {node.description}
					</NavLink>
				</ListGroupItem>
			)
		})
	}

	render() {
		return (
			<Fragment>
				{this.props.dataTypes.length > 0 && <Card>
					<CardBody>
						<ListGroup>
							Data Types: {this.renderdatatypes()}
						</ListGroup>
					</CardBody>
				</Card>}
			</Fragment>
		);
	}
}

export default connect()(DataTypes);