import React, { Fragment, Component } from 'react';
import { Col } from 'reactstrap';
import { connect } from 'react-redux';
import ReleaseVersions from '../../components/ReleaseVersions';
import AdminEditReleaseVersion from '../../components/admin/EditReleaseVersion';
import { loadReleaseVersion, loadReleaseVersions, loadReleaseVersionSnapshots } from '../../actions/releaseVersionActions';

class AdminReleaseVersions extends Component {

	getData(releaseVersion) {
		this.props.dispatch(loadReleaseVersion(releaseVersion));
		this.props.dispatch(loadReleaseVersionSnapshots(releaseVersion));
	}

	componentDidUpdate(prevProps) {
		//console.log("AdminDataTypes: Update: ", this.props);
		if (this.props.match.params.releaseVersion !== prevProps.match.params.releaseVersion) {
			this.getData(this.props.match.params.releaseVersion);
		}
	}

	componentDidMount() {
		this.props.dispatch(loadReleaseVersions());
		var match = this.props.match;
		//console.log("AdminDataTypes: Did Mount: ", this.props);
		if (match != null && match.params != null && match.params.releaseVersion != null) {
			this.getData(this.props.match.params.releaseVersion);
		}
	}

	render() {
		return (
			<Fragment>
				<Col xs={{ order: 2 }} md={{ size: 4, order: 1 }} tag="aside" className="pb-5 mb-5 pb-md-0 mb-md-0 mx-auto mx-md-0">
					{ this.props.releaseVersions && <ReleaseVersions releaseVersions={this.props.releaseVersions} />}
				</Col>

				<Col xs={{ order: 1 }} md={{ size: 7, offset: 1 }} tag="section" className="py-5 mb-5 py-md-0 mb-md-0">
					{this.props.releaseVersion == null && <span>Please choose a Release Version</span>}
					{this.props.releaseVersion != null && <AdminEditReleaseVersion />}
				</Col>
			</Fragment>
		)
	}
}

const mapStateToProps = (state) => {
	//console.log("State: ", state);
	return {
		releaseVersion: state.releaseVersionReducer.releaseVersion,
		releaseVersions: state.releaseVersionReducer.releaseVersions,
		snapshots: state.releaseVersionReducer.snapshots
	}
}

export default connect(mapStateToProps)(AdminReleaseVersions);