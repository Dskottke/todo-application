import React from 'react';
import Navbar from "./Navbar.jsx";
import AddModal from "./AddModal.jsx";
import Hero from "./Hero.jsx";
import BoardOverview from "./BoardOverview.jsx";
import {useGlobalContext} from "../Context.jsx";
import {useAuth} from "../hooks.js";
import SideBar from "./SideBar.jsx";

function MainPage() {
    const user = useAuth()
    const {isModalOpen} = useGlobalContext()

    return (
        <section>
            {user &&
                <>
                    <Navbar/>
                    <SideBar/>
                    {isModalOpen && <AddModal/>}
                    <Hero/>
                    <BoardOverview/>
                </>
            }
        </section>

    );
}

export default MainPage;