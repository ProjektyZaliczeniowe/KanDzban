import React, { Component } from "react";
import { Link } from "react-router-dom";

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
    const { login, users } = this.state;
    return (
      <div className="col-md-6 col-md-offset-3">
        <h1>Hi {login}!</h1>
        <p>You're logged in </p>
        <p>
          <Link to="/home" onClick={this.handleLogOut}>
            Logout
          </Link>
        </p>
      </div>
    );
  }
}

export default MainPage;
