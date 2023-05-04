import React from 'react';
import Navbar from "./Navbar.jsx";
import AddModal from "./AddModal.jsx";
import Hero from "./Hero.jsx";
import BoardOverview from "./BoardOverview.jsx";
import {ToastContainer} from "react-toastify";
import {useGlobalContext} from "../Context.jsx";

function WebSite() {
    const {isModalOpen} = useGlobalContext()
    return (<div>
        <Navbar/>
        {isModalOpen && <AddModal/>}
        <Hero/>
        <BoardOverview/>
        <ToastContainer position={"top-center"}/>
    </div>);
}

export default WebSite;