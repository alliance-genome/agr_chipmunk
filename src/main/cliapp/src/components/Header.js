import React from 'react';
import { NavLink as RRNavLink } from 'react-router-dom';

import { Container, Row, Col, Navbar, Nav, NavLink, NavItem } from 'reactstrap';

const Header = () => {

    return (
        <header>
            <Navbar fixed="top" light expand="xs" className="border-bottom border-gray bg-white" style={{ height: 50 }}>

                <Container>
                    <Row noGutters className="position-relative w-100 align-items-center">

                        <Col className="justify-content-start">
                            <Nav className="mrx-auto" navbar>
                                <NavItem><NavLink tag={RRNavLink} to="/">Home</NavLink></NavItem>
                                <NavItem><NavLink tag={RRNavLink} to="/datafiles">Data Files</NavLink></NavItem>
                                <NavItem><NavLink tag={RRNavLink} to="/admin">Admin</NavLink></NavItem>
                                <NavItem><NavLink tag={RRNavLink} to="/swagger-ui">Swagger API</NavLink></NavItem>
                            </Nav>
                        </Col>

                        <Col className="justify-content-between">
                            <Nav></Nav>
                        </Col>

                        <Col className="justify-content-end">
                            <Nav navbar>
                                <NavItem><NavLink tag={RRNavLink} to="/login">Login</NavLink></NavItem>
                            </Nav>
                        </Col>

                    </Row>
                </Container>

            </Navbar>
        </header>
    );
}


export default Header;
