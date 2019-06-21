import React, { Component } from "react";

import { Navbar, Nav } from "react-bootstrap";
import { userService } from "../services/userService";

class MainPage extends Component {
  constructor(props) {
    super(props);

    this.state = {
      login: ""
    };
  }

  componentDidMount() {
    this.setState({
      login: localStorage.getItem("login")
    });
  }

  handleLogOut() {
    userService.logout();
  }

  render() {
    const { login } = this.state;
    return (
      <div>
        <Navbar bg="dark" variant="dark">
          <Navbar.Brand href="home">KANBANBoard</Navbar.Brand>
          <Nav className="mr-auto">
            <Nav.Link href="#projects">Projects</Nav.Link>
            <Nav.Link href="board">Board</Nav.Link>
            <Nav.Link href="#account">My account</Nav.Link>
            <Nav.Link href="home" onClick={this.handleLogOut}>
              Logout
            </Nav.Link>
          </Nav>
        </Navbar>
        <div className="col-md-6 col-md-offset-3">
          <h1>Hi {login}!</h1>
          <p>You're logged in </p>
        </div>
      </div>
    );
  }
}

export default MainPage;
