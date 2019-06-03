import React, { Component } from "react";
import { Navbar, Nav } from "react-bootstrap";
import { userService } from "../services/userService";

class HomePage extends Component {
  constructor(props) {
    super(props);
  }

  componentDidMount() {}

  handleLogOut() {
    userService.logout();
  }

  render() {
    return (
      <div>
        <Navbar bg="dark" variant="dark">
          <Navbar.Brand href="home">KANBANBoard</Navbar.Brand>
          <Nav className="mr-auto">
            <Nav.Link href="login">Sign in</Nav.Link>
            <Nav.Link href="register">Sign up</Nav.Link>
          </Nav>
        </Navbar>
        <div>
          <p>Projekt KANBAN</p>
        </div>
      </div>
    );
  }
}

export default HomePage;
