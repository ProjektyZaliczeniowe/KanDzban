import React from "react";
import "./App.css";
import { BrowserRouter as Router, Route } from "react-router-dom";

import LoginPage from "./components/LoginPage";
import RegisterPage from "./components/RegisterPage";
import MainPage from "./components/MainPage";
import HomePage from "./components/HomePage";
import BoardPage from "./components/BoardPage";

function App() {
  return (
    <Router>
      <div>
        <Route exact path="/" component={MainPage} />
        <Route path="/home" component={HomePage} />
        <Route path="/login" component={LoginPage} />
        <Route path="/register" component={RegisterPage} />
        <Route path="/board" component={BoardPage} />
      </div>
    </Router>
  );
}

export default App;
