import React, { Component } from "react";
import "../css/BoardPage.css";

import { boardService } from "../services/boardService";
import { Navbar, Nav } from "react-bootstrap";

class BoardPage extends Component {
  constructor(props) {
    super(props);
    {
      this.getUserBoards();
    }
  }

  state = {
    boardsList: [],
    board: "",
    boardTable: ""
  };

  getUserBoards = () => {
    boardService.getUserBoards("testUser").then(
      user => {
        this.setState({ boardsList: user });
      },
      error => {
        this.setState({ error, loading: false });
      }
    );
  };

  showBoards() {
    const { boardsList } = this.state;
    return (
      <ul>
        {boardsList.map((value, index) => {
          return (
            <Nav.Link onClick={() => this.setBoard(value)} className="board-1">
              {value["name"]}
            </Nav.Link>
          );
        })}
      </ul>
    );
  }

  showRightBoard() {
    const { board } = this.state;
    if (board != "") {
      return (
        <div id="labels">
          {board["labelList"].map(value => {
            return (
              <div id="labelX">
                <ul class="list-group">
                  <Nav.Link id="colName">{value["name"]}</Nav.Link>
                  {value["taskList"].map(value2 => {
                    return (
                      <Nav.Link
                        onClick={() => this.setBoard(value)}
                        className="task"
                      >
                        {value2["name"]}
                      </Nav.Link>
                    );
                  })}
                </ul>
              </div>
            );
          })}
        </div>
      );
    }
  }

  setBoard = actualBoard => {
    this.setState({ board: actualBoard });
  };

  render() {
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
        <Navbar>
          <div className="col-md-3 col-md-offset-2" />
        </Navbar>
        <div id="lewaCol">{this.showBoards()}</div>
        <div id="prawaCol">{this.showRightBoard()}</div>
      </div>
    );
  }
}

export default BoardPage;
