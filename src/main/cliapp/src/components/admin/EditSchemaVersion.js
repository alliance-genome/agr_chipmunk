import React, { Component } from 'react';
import { connect } from 'react-redux';

//import "@kenshooui/react-multi-select/dist/style.css"
//import MultiSelect from "@kenshooui/react-multi-select";
//import { loadSchemaVersions } from '../../actions/schemaVersionActions';
import RenderSchemaVersion from '../../components/RenderSchemaVersion';


class AdminEditSchemaVersion extends Component {

	render() {
		if(Object.keys(this.props.schemaVersion).length===0) {
			return (
				<div>Please Choose a schema Version</div>
			);
		} else {
			return (
				<RenderSchemaVersion schemaVersion={this.props.schemaVersion} />
			);
		}
	};
}

const mapStateToProps = (state) => {
    //console.log("State: ", state);
    return {
        schemaVersion: state.schemaVersionReducer.schemaVersion
    }
}

export default connect(mapStateToProps)(AdminEditSchemaVersion);