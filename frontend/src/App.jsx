import React from 'react'

import MainPage from "./mainpage/MainPage.jsx";
import {Route, Routes} from "react-router-dom";
import LoginPage from "./login/LoginPage.jsx";

function App() {


    return (
        <section className="App">
            <Routes>
                <Route path={"/"} element={<LoginPage/>}/>
                <Route path={"/main"} element={<MainPage/>}/>
            </Routes>
        </section>
    )
}

export default App
