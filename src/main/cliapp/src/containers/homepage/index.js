import React, { Fragment, Component } from 'react';
import { Col } from 'reactstrap';

import Post from '../../components/Post';
import Header from '../../components/Header';

import { connect } from 'react-redux';

class Homepage extends Component {
	render() {
		return (
			<Fragment>
				<Header />
				<Col xs={{ order: 2 }} md={{ size: 4, order: 1 }} tag="aside" className="pb-5 mb-5 pb-md-0 mb-md-0 mx-auto mx-md-0">
					No Side Card Here
        		</Col>

				<Col xs={{ order: 1 }} md={{ size: 7, offset: 1 }} tag="section" className="py-5 mb-5 py-md-0 mb-md-0">
					<Post />
				</Col>
			</Fragment>
		);
	}
}

const mapStateToProps = (state) => {
	return {
		dataTypes: state.dataTypes
	}
}

export default connect(mapStateToProps)(Homepage);