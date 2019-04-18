import React, { Component, Fragment } from 'react';
import { CardBody, Card } from 'reactstrap';
import { connect } from 'react-redux';

import DataTypeList from './DataTypeList';

import { loadDataTypes } from '../actions/dataTypeActions';

class SideCard extends Component {

	componentDidMount() {
		this.props.dispatch(loadDataTypes());
	}

	render() {

		return (
			<Fragment>
				{this.props.dataTypes && <Card>
					<CardBody>
						Data Types:
						<DataTypeList data={this.props.dataTypes} />
					</CardBody>
				</Card>}
			</Fragment>
		);
	}
}

const mapStateToProps = (state) => {
	//console.log("State: ", state);
	return {
		dataType: state.dataTypeReducer.dataType,
		dataTypes: state.dataTypeReducer.dataTypes
	}
}

export default connect(mapStateToProps)(SideCard);
