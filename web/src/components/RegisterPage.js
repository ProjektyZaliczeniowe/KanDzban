import React, { Component } from "react";
import { Navbar, Nav } from "react-bootstrap";
import { userService } from "../services/userService";
class RegisterPage extends Component {
  constructor(props) {
    super(props);

    userService.logout();

    this.state = {
      username: "",
      email: "",
      password: "",
      repeatedPassword: "",
      submitted: false,
      loading: false,
      error: ""
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChange(e) {
    const { name, value } = e.target;
    this.setState({ [name]: value });
  }

  handleSubmit(e) {
    e.preventDefault();

    this.setState({ submitted: true });
    const { username, email, password, repeatedPassword } = this.state;

    // stop here if form is invalid
    if (!(username && email && password && repeatedPassword)) {
      return;
    }

    if (password !== repeatedPassword) {
      this.setState({ error: "Passwords must be the same" });
    }

    this.setState({ loading: true });
    userService.register(username, email, password).then(
      user => {
        const { from } = this.props.location.state || {
          from: { pathname: "/login" }
        };
        this.props.history.push(from);
      },
      error => {
        this.setState({ error, loading: false });
      }
    );
  }

  render() {
    const {
      username,
      email,
      password,
      repeatedPassword,
      submitted,
      loading,
      error
    } = this.state;
    return (
      <div>
        <Navbar bg="dark" variant="dark">
          <Navbar.Brand href="home">KANBANBoard</Navbar.Brand>
          <Nav className="mr-auto">
            <Nav.Link href="login">Sign in</Nav.Link>
            <Nav.Link href="register">Sign up</Nav.Link>
          </Nav>
        </Navbar>
        <div className="col-md-6 col-md-offset-3">
          <h2>Register</h2>
          <form name="form" onSubmit={this.handleSubmit}>
            <div
              className={
                "form-group" + (submitted && !username ? " has-error" : "")
              }
            >
              <label htmlFor="username">Username</label>
              <input
                type="text"
                className="form-control"
                name="username"
                value={username}
                onChange={this.handleChange}
              />
              {submitted && !username && (
                <div className="help-block">Username is required</div>
              )}
            </div>
            <div
              className={
                "form-group" + (submitted && !email ? " has-error" : "")
              }
            >
              <label htmlFor="email">Email</label>
              <input
                type="text"
                className="form-control"
                name="email"
                value={email}
                onChange={this.handleChange}
              />
              {submitted && !email && (
                <div className="help-block">Email is required</div>
              )}
            </div>
            <div
              className={
                "form-group" + (submitted && !password ? " has-error" : "")
              }
            >
              <label htmlFor="password">Password</label>
              <input
                type="password"
                className="form-control"
                name="password"
                value={password}
                onChange={this.handleChange}
              />
              {submitted && !password && (
                <div className="help-block">Password is required</div>
              )}
            </div>
            <div
              className={
                "form-group" +
                (submitted && !repeatedPassword ? " has-error" : "")
              }
            >
              <label htmlFor="repeatedPassword">Repeat password</label>
              <input
                type="password"
                className="form-control"
                name="repeatedPassword"
                value={repeatedPassword}
                onChange={this.handleChange}
              />
              {submitted && !repeatedPassword && (
                <div className="help-block">Repeat password is required</div>
              )}
            </div>
            <div className="form-group">
              <button className="btn btn-primary" disabled={loading}>
                Register
              </button>
              {loading && (
                <img src="data:image/gif;base64,R0lGODlhEAAQAPIAAP///wAAAMLCwkJCQgAAAGJiYoKCgpKSkiH/C05FVFNDQVBFMi4wAwEAAAAh/hpDcmVhdGVkIHdpdGggYWpheGxvYWQuaW5mbwAh+QQJCgAAACwAAAAAEAAQAAADMwi63P4wyklrE2MIOggZnAdOmGYJRbExwroUmcG2LmDEwnHQLVsYOd2mBzkYDAdKa+dIAAAh+QQJCgAAACwAAAAAEAAQAAADNAi63P5OjCEgG4QMu7DmikRxQlFUYDEZIGBMRVsaqHwctXXf7WEYB4Ag1xjihkMZsiUkKhIAIfkECQoAAAAsAAAAABAAEAAAAzYIujIjK8pByJDMlFYvBoVjHA70GU7xSUJhmKtwHPAKzLO9HMaoKwJZ7Rf8AYPDDzKpZBqfvwQAIfkECQoAAAAsAAAAABAAEAAAAzMIumIlK8oyhpHsnFZfhYumCYUhDAQxRIdhHBGqRoKw0R8DYlJd8z0fMDgsGo/IpHI5TAAAIfkECQoAAAAsAAAAABAAEAAAAzIIunInK0rnZBTwGPNMgQwmdsNgXGJUlIWEuR5oWUIpz8pAEAMe6TwfwyYsGo/IpFKSAAAh+QQJCgAAACwAAAAAEAAQAAADMwi6IMKQORfjdOe82p4wGccc4CEuQradylesojEMBgsUc2G7sDX3lQGBMLAJibufbSlKAAAh+QQJCgAAACwAAAAAEAAQAAADMgi63P7wCRHZnFVdmgHu2nFwlWCI3WGc3TSWhUFGxTAUkGCbtgENBMJAEJsxgMLWzpEAACH5BAkKAAAALAAAAAAQABAAAAMyCLrc/jDKSatlQtScKdceCAjDII7HcQ4EMTCpyrCuUBjCYRgHVtqlAiB1YhiCnlsRkAAAOwAAAAAAAAAAAA==" />
              )}
            </div>
            {error && <div className={"alert alert-danger"}>{error}</div>}
          </form>
        </div>
      </div>
    );
  }
}

export default RegisterPage;
