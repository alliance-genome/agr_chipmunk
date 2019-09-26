import React, { Component } from 'react';
import { connect } from 'react-redux';

//import "@kenshooui/react-multi-select/dist/style.css"
//import MultiSelect from "@kenshooui/react-multi-select";
//import { loadReleaseVersions } from '../../actions/releaseVersionActions';
import RenderReleaseVersion from '../../components/RenderReleaseVersion';


class AdminEditReleaseVersion extends Component {

	render() {
		if(Object.keys(this.props.releaseVersion).length===0) {
			return (
				<div>Please Choose a release Version</div>
			);
		} else {
			return (
				<RenderReleaseVersion releaseVersion={this.props.releaseVersion} snapshots={this.props.snapshots} />
			);
		}
	};
}

const mapStateToProps = (state) => {
    //console.log("State: ", state);
    return {
        releaseVersion: state.releaseVersionReducer.releaseVersion,
        snapshots: state.releaseVersionReducer.snapshots
    }
}

export default connect(mapStateToProps)(AdminEditReleaseVersion);