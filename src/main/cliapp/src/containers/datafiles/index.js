import React, { Fragment, Component } from 'react';
import { Col } from 'reactstrap';
import { loadDataFiles } from '../../actions/dataFileActions';
import { connect } from 'react-redux';

import SideCard from '../../components/SideCard';
import DataFileList from '../../components/DataFileList';

class DataFiles extends Component {

    componentDidUpdate(prevProps) {
        if (this.props.match.params.dataType !== prevProps.match.params.dataType ||
            this.props.match.params.dataSubType !== prevProps.match.params.dataSubType) {
            this.props.dispatch(loadDataFiles(this.props.match.params.dataType, this.props.match.params.dataSubType));
        }
    }

    componentDidMount() {
        if (this.props.match.params.dataType != null && this.props.match.params.dataSubType != null) {
            this.props.dispatch(loadDataFiles(this.props.match.params.dataType, this.props.match.params.dataSubType));
        }
    }

    render() {
        return (
            <Fragment>
                <Col xs={{ order: 2 }} md={{ size: 4, order: 1 }} tag="aside" className="pb-5 mb-5 pb-md-0 mb-md-0 mx-auto mx-md-0">
                    <SideCard />
                </Col>

                <Col xs={{ order: 1 }} md={{ size: 7, offset: 1 }} tag="section" className="py-5 mb-5 py-md-0 mb-md-0">
                    <DataFileList data={this.props.dataFiles} />
                </Col>
            </Fragment>
        )
    }
}

const mapStateToProps = (state) => {
    console.log("State: ", state);
    return {
        dataFiles: state.dataFileReducer.dataFiles
    }
}

export default connect(mapStateToProps)(DataFiles);

