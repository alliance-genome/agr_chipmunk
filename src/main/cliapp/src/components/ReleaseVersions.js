import React, { Component, Fragment } from 'react';
import { NavLink as RRNavLink } from 'react-router-dom';
import { connect } from 'react-redux';

import { CardBody, Card, ListGroup, ListGroupItem, NavLink } from 'reactstrap';

class ReleaseVersions extends Component {

	render() {
		return (
			<Fragment>
				{this.props.releaseVersions.length > 0 && 
					<Card>
						<CardBody>
							<ListGroup>
								{this.props.releaseVersions.map((releaseversion, index) => {
									return (<ListGroupItem className="text-nowrap" key={releaseversion.id}>
										<NavLink to={"/admin/releaseversion/" + releaseversion.id} tag={RRNavLink}>
											{releaseversion.releaseVersion}
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

export default connect()(ReleaseVersions);