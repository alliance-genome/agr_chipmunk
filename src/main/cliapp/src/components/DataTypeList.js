import React from 'react';
import { NavLink as RRNavLink } from 'react-router-dom';
import { Navbar, NavItem, NavLink, Nav, UncontrolledTooltip } from 'reactstrap';

class DataTypeList extends React.Component {

	datasubtypes(parentname, data) {
		if (data) {
			return data.map((node, index) => {
				let path = '/datafiles/' + parentname + '/' + node.name;
				return (
					<NavLink key={node.id} to={path} tag={RRNavLink}>{node.name}</NavLink>
				)
			})
		}
	}

	datatypes(data) {
		return data.map((node, index) => {
			return (
				<NavItem key={node.id} id={node.name}>{node.name}
					<UncontrolledTooltip target={node.name}>{node.description}</UncontrolledTooltip>
					<Nav vertical>{this.datasubtypes(node.name, node.dataSubTypes)}</Nav>
				</NavItem>
			)
		})
	}

	render() {
		return <Navbar><Nav vertical>{this.datatypes(this.props.data)}</Nav></Navbar>
	}
};

export default DataTypeList;
