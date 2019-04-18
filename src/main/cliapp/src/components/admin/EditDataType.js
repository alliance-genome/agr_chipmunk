import React, { Component } from 'react';

import "@kenshooui/react-multi-select/dist/style.css"
import MultiSelect from "@kenshooui/react-multi-select";
import { loadDataSubTypes } from '../../actions/dataSubTypeActions';
import { connect } from 'react-redux';

import { Button, ListGroupItem, ListGroup, Form, FormGroup, Label, Input, Card, CardBody, CardTitle } from 'reactstrap';

class AdminEditDataType extends Component {

	state = { form_data: { } };
	formRef = React.createRef();

	constructor(props) {
		super(props);
		this.handleSelectChange = this.handleSelectChange.bind(this);
	}

	componentDidMount() {
		console.log(this.props);
		this.formRef.current.reset()
		this.props.dispatch(loadDataSubTypes());
		this.setState({ form_data: this.props.dataType});
	}

	componentDidUpdate(prevProps) {
		//console.log(this.props);
		if (this.props.dataType.id !== prevProps.dataType.id) {
			this.formRef.current.reset();
			this.setState({ form_data: this.props.dataType});
		}
	}

	renderSchemaFiles(schemaFiles) {
		if(schemaFiles != null) {
			return schemaFiles.map((node, index) => {
				return (
					<ListGroup key={ node.id }>
						<ListGroupItem >{ node.schemaVersion.schema } -> { node.filePath }</ListGroupItem>
					</ListGroup>
				)
			})
		}
	}

	renderDataSubTypes(dataSubTypes) {
		if(dataSubTypes != null) {
			return dataSubTypes.map((node, index) => {
				return (
					<ListGroup key={ node.id }>
						<ListGroupItem >{ node.name }</ListGroupItem>
					</ListGroup>
				)
			})
		}
	}

	handleSelectChange(selectedItems) {
		console.log("Selected Items: ", selectedItems);

		const { form_data } = this.state;
		const newFormData = {
			...form_data,
			dataSubTypes: selectedItems
		};

		this.setState({ form_data: newFormData });
		//this.setState({ selectedItems });
	}

	changeHandler = (event) => {
		const name = event.target.name;
		const value = event.target.type === 'checkbox' ? event.target.checked : event.target.value;
		
		const { form_data } = this.state;
		const newFormData = {
			...form_data,
			[name]: value
		};

		this.setState({ form_data: newFormData });
		//console.log("Change: Json: " + JSON.stringify(this.state));
	}

	saveDataType = (event) => {
		event.preventDefault();
		//this.props.data = this.state.form_data;
		console.log("saveDataType: Json: " + JSON.stringify(this.state.form_data));
	}

	render() {
		return (
			<Card>
				<CardBody>
					<CardTitle>ID: {this.props.dataType.id }</CardTitle>
					<Form onSubmit={ this.saveDataType } innerRef={this.formRef}>
						<FormGroup>
							<Label for="name">Name:</Label>
							<Input name="name" defaultValue={ this.props.dataType.name } onChange={ this.changeHandler } />
						</FormGroup>
						<FormGroup>
							<Label for="description">Description:</Label>
							<Input name="description" defaultValue={ this.props.dataType.description } onChange={ this.changeHandler } />
						</FormGroup>
						<FormGroup>
							<Label for="fileExtension">File Extension (Leave off the ".")</Label>
							<Input name="fileExtension" defaultValue={ this.props.dataType.fileExtension } onChange={ this.changeHandler } />
						</FormGroup>
						<FormGroup check>
							<Label check>
								<Input name="dataSubTypeRequired" type="checkbox" defaultChecked={ this.props.dataType.dataSubTypeRequired } onChange={ this.changeHandler } />{' '}Does this Data Type have sub types?
							</Label>
						</FormGroup>
						<FormGroup check>
							<Label check>
								<Input name="validationRequired" type="checkbox" defaultChecked={ this.props.dataType.validationRequired } onChange={ this.changeHandler } />{' '}Will this Data Type be validated?
							</Label>
						</FormGroup>
						<FormGroup>
							<Label for="schema_files">Schema Files:</Label>
							{ this.renderSchemaFiles(this.props.dataType.schemaFiles) }
						</FormGroup>
						<FormGroup>
							<Label for="data_subtypes">Sub Types:</Label>
							<MultiSelect items={this.props.dataSubTypes} selectedItems={this.state.form_data.dataSubTypes} onChange={this.handleSelectChange} />
						</FormGroup>
						<Button type="submit">Submit</Button>
					</Form>
				</CardBody>
			</Card>

	
		);
	}
}

const mapStateToProps = (state) => {
    //console.log("State: ", state);
    return {
        dataSubTypes: state.dataSubTypeReducer.dataSubTypes
    }
}

export default connect(mapStateToProps)(AdminEditDataType);
