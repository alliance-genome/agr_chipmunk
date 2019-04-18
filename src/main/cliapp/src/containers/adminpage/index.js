import React, { Fragment, Component } from 'react';
import { NavLink as RRNavLink } from 'react-router-dom';
import { NavLink, Col, Nav, CardBody } from 'reactstrap';

//import axios from 'axios';

//import DataTypes from '../../components/AdminTypes';

class Admin extends Component {

	render() {
		return (
			<Fragment>
				<Col xs={{ order: 2 }} md={{ size: 4, order: 1 }} tag="aside" className="pb-5 mb-5 pb-md-0 mb-md-0 mx-auto mx-md-0">
					<CardBody>
						<Nav vertical>
							<NavLink to="/admin/datatypes" tag={RRNavLink}>Data Types</NavLink>
							<NavLink to="/admin/datafiles" tag={RRNavLink}>Data Files</NavLink>
							<NavLink to="/admin/schemaversions" tag={RRNavLink}>Schema Versions</NavLink>
							<NavLink to="/admin/releaseversions" tag={RRNavLink}>Release Versions</NavLink>
							<NavLink to="/admin/uploadfiles" tag={RRNavLink}>Uploaded Files</NavLink>
							<NavLink to="/admin/snapshots" tag={RRNavLink}>Snapshots</NavLink>
						</Nav>
					</CardBody>
				</Col>

				<Col xs={{ order: 1 }} md={{ size: 7, offset: 1 }} tag="section" className="py-5 mb-5 py-md-0 mb-md-0">
					Please Choose something to administrator
				</Col>
			</Fragment>
		)
	}
}

export default Admin;