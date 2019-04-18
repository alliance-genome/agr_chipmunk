import React, { Fragment, Component } from 'react';
import { Col } from 'reactstrap';
import { connect } from 'react-redux';
import DataTypes from '../../components/DataTypes';
import AdminEditDataType from '../../components/admin/EditDataType';
import { loadDataType, loadDataTypes } from '../../actions/dataTypeActions';

class AdminDataTypes extends Component {

	getData(dataType) {
		this.props.dispatch(loadDataType(dataType));
	}

	componentDidUpdate(prevProps) {
		//console.log("AdminDataTypes: Update: ", this.props);
		if (this.props.match.params.dataType !== prevProps.match.params.dataType) {
			this.getData(this.props.match.params.dataType);
		}
	}

	componentDidMount() {
		this.props.dispatch(loadDataTypes());
		var match = this.props.match;
		//console.log("AdminDataTypes: Did Mount: ", this.props);
		if (match != null && match.params != null && match.params.dataType != null) {
			this.getData(this.props.match.params.dataType);
		}
	}

	render() {
		return (
			<Fragment>
				<Col xs={{ order: 2 }} md={{ size: 4, order: 1 }} tag="aside" className="pb-5 mb-5 pb-md-0 mb-md-0 mx-auto mx-md-0">
					{ this.props.dataTypes && <DataTypes dataTypes={this.props.dataTypes} />}
				</Col>

				<Col xs={{ order: 1 }} md={{ size: 7, offset: 1 }} tag="section" className="py-5 mb-5 py-md-0 mb-md-0">
					{this.props.dataType == null && <span>Please choose a data type</span>}
					{this.props.dataType != null && <AdminEditDataType dataType={this.props.dataType} />}
				</Col>
			</Fragment>
		)
	}
}

const mapStateToProps = (state) => {
	//console.log("State: ", state);
	return {
		dataType: state.dataTypeReducer.dataType,
		dataTypes: state.dataTypeReducer.dataTypes
	}
}

export default connect(mapStateToProps)(AdminDataTypes);