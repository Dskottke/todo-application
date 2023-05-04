import React from 'react'

import MainPage from "./mainpage/MainPage.jsx";
import {Route, Routes} from "react-router-dom";
import LoginPage from "./login/LoginPage.jsx";
import {ToastContainer} from "react-toastify";

function App() {

    return (
        <section className="App">
            <Routes>
                <Route path={"/"} element={<LoginPage/>}/>
                <Route path={"/main"} element={<MainPage/>}/>
            </Routes>
            <ToastContainer position={"top-center"}/>
        </section>
    )
}

export default App
