import React from "react";
import "./App.css";
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import { Navbar, Nav } from "react-bootstrap";
import LoginPage from "./components/LoginPage";
import RegisterPage from "./components/RegisterPage";

function App() {
  return (
    <Router>
      <div>
        <Navbar bg="dark" variant="dark">
          <Navbar.Brand href="/">KANBANBoard</Navbar.Brand>
          <Nav className="mr-auto">
            <Nav.Link href="login">Sign in</Nav.Link>
            <Nav.Link href="register">Sign up</Nav.Link>
          </Nav>
        </Navbar>

        <hr />

        <Route exact path="/" component={Home} />
        <Route path="/login" component={LoginPage} />
        <Route path="/register" component={RegisterPage} />
      </div>
    </Router>
  );
}
function Home() {
  return (
    <div>
      <p> HOME</p>
    </div>
  );
}

export default App;
