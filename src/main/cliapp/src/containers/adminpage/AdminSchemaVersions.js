import React, { Fragment, Component } from 'react';
import { Col } from 'reactstrap';
import { connect } from 'react-redux';
import SchemaVersions from '../../components/SchemaVersions';
import AdminEditSchemaVersion from '../../components/admin/EditSchemaVersion';
import { loadSchemaVersion, loadSchemaVersions } from '../../actions/schemaVersionActions';

class AdminSchemaVersions extends Component {

	getData(schemaVersion) {
		this.props.dispatch(loadSchemaVersion(schemaVersion));
	}

	componentDidUpdate(prevProps) {
		//console.log("AdminDataTypes: Update: ", this.props);
		if (this.props.match.params.schemaVersion !== prevProps.match.params.schemaVersion) {
			this.getData(this.props.match.params.schemaVersion);
		}
	}

	componentDidMount() {
		this.props.dispatch(loadSchemaVersions());
		var match = this.props.match;
		//console.log("AdminDataTypes: Did Mount: ", this.props);
		if (match != null && match.params != null && match.params.schemaVersion != null) {
			this.getData(this.props.match.params.schemaVersion);
		}
	}

	render() {
		return (
			<Fragment>
				<Col xs={{ order: 2 }} md={{ size: 4, order: 1 }} tag="aside" className="pb-5 mb-5 pb-md-0 mb-md-0 mx-auto mx-md-0">
					{ this.props.schemaVersions && <SchemaVersions schemaVersions={this.props.schemaVersions} />}
				</Col>

				<Col xs={{ order: 1 }} md={{ size: 7, offset: 1 }} tag="section" className="py-5 mb-5 py-md-0 mb-md-0">
					{this.props.schemaVersion == null && <span>Please choose a Schema Version</span>}
					{this.props.schemaVersion != null && <AdminEditSchemaVersion data={this.props.schemaVersion} />}
				</Col>
			</Fragment>
		)
	}
}

const mapStateToProps = (state) => {
	//console.log("State: ", state);
	return {
		schemaVersion: state.schemaVersionReducer.schemaVersion,
		schemaVersions: state.schemaVersionReducer.schemaVersions
	}
}

export default connect(mapStateToProps)(AdminSchemaVersions);