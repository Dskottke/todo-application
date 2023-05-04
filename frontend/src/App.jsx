import React from 'react'

import WebSite from "./components/WebSite.jsx";
import {Route, Routes} from "react-router-dom";
import Login from "./components/Login.jsx";

function App() {


    return (
        <div className="App">
            <Routes>
                <Route path={"/"} element={<Login/>}/>
                <Route path={"/main"} element={<WebSite/>}/>
            </Routes>
        </div>
    )
}

export default App
