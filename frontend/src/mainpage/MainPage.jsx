import React from 'react';
import Navbar from "./Navbar.jsx";
import AddModal from "./AddModal.jsx";
import Hero from "./Hero.jsx";
import BoardOverview from "./BoardOverview.jsx";
import {useGlobalContext} from "../Context.jsx";

function MainPage() {
    const {isModalOpen} = useGlobalContext()
    return (<div>
        <Navbar/>
        {isModalOpen && <AddModal/>}
        <Hero/>
        <BoardOverview/>
    </div>);
}

export default MainPage;