import logo from "./logo.svg";
import React, { useState } from "react";
import axios from "axios";
import "./App.css";

function App() {
  const [data, setData] = useState(null);
  const getData = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/teams/ranks");
      setData(response);
      console.logo(data);
    } catch (e) {
      console.log(e);
    }
  };
  getData();
  console.log(1);
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}

export default App;
