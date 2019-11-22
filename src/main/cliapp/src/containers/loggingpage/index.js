import React, { Fragment, Component } from 'react';
import Collapsible from 'react-collapsible';
import { ListGroupItem, ListGroup, Badge } from 'reactstrap';
import { w3cwebsocket as W3CWebSocket } from "websocket";
import { Col } from 'reactstrap';

import Header from '../../components/Header';


const URL = (window.location.protocol === 'https:' ? "wss" : "ws") + '://' + window.location.hostname + ':' + (window.location.port === "3000" ? "8080" : window.location.port) + '/apilog';

class LoggingPage extends Component {
	
	state = {
		messages: []
	}

	client = new W3CWebSocket(URL);

	componentWillMount() {
		//console.log(URL);
		
		this.client.onopen = () => {
			console.log('WebSocket Client Connected');
		};
		this.client.onmessage = (message) => {
      		this.addMessage(JSON.parse(message.data))
			console.log(JSON.parse(message.data));
		};
	}

	addMessage = message => this.setState(
		state => ({ messages: [message, ...state.messages] })
	);

	render() {

		return (
			<Fragment>
				<Header />
				<Col tag="section" className="py-5 mb-5 py-md-0 mb-md-0">
					{this.state.messages.map((message, index) =>
						<Collapsible key={index} trigger={message.address + " -> " + message.requestMethod + ": " + message.requestUri}>
							<ListGroupItem key={index}>
								<ListGroup>
									<ListGroupItem>{ message.requestUri } <Badge pill href={ message.requestUri }>Run</Badge></ListGroupItem>
									<ListGroupItem>headersString: { JSON.stringify(message.headersString) }</ListGroupItem>
									<ListGroupItem>pathParameters: { JSON.stringify(message.pathParameters) }</ListGroupItem>
									<ListGroupItem>queryParameters: { JSON.stringify(message.queryParameters) }</ListGroupItem>
									<ListGroupItem hidden>{ JSON.stringify(message) }</ListGroupItem>
								</ListGroup>
							</ListGroupItem>
						</Collapsible>
					)}
				</Col>
			</Fragment>
		);

	}
}

export default LoggingPage;