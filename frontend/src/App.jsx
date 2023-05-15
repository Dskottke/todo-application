import React from 'react'

import MainPage from "./mainpage/MainPage.jsx";
import {Route, Routes, useLocation} from "react-router-dom";
import {ToastContainer} from "react-toastify";
import FormPage from "./formpage/FormPage.jsx";
import {useGlobalContext} from "./Context.jsx";
import AdminOverview from "./admin/AdminOverview.jsx";
import Navbar from "./mainpage/Navbar.jsx";

function App() {

    const {fetchUser, signUpUser} = useGlobalContext()
    const location = useLocation();

    return (
        <section className="App">
            {location.pathname !== '/login' && <Navbar/>}
            <Routes>
                <Route path={"/"} element={<MainPage/>}/>
                <Route path={"/login"} element={
                    <FormPage
                        title={"Login"}
                        submitButtonText={"Login"}
                        secondaryButtonText={"Sign Up"}
                        submitFunction={fetchUser}
                        navLink={"/signup"}
                    />}/>
                <Route path={"/signup"} element={
                    <FormPage
                        title={"Sign Up"}
                        submitButtonText={"Sign Up"}
                        secondaryButtonText={"back"}
                        submitFunction={signUpUser}
                        navLink={"/login"}
                    />}/>
                <Route path={"/admin"} element={<AdminOverview/>}/>
            </Routes>
            <ToastContainer position={"top-center"}/>
        </section>
    )
}

export default App
