import React from 'react';
import Navbar from "./Navbar.jsx";
import AddModal from "./AddModal.jsx";
import Hero from "./Hero.jsx";
import BoardOverview from "./BoardOverview.jsx";
import {useGlobalContext} from "../Context.jsx";
import {useAuth, useUnconfirmedUsers} from "../hooks.js";
import SideBar from "./SideBar.jsx";
import AdminOverview from "../admin/AdminOverview.jsx";

function MainPage() {
    const user = useAuth()
    const {isModalOpen, unconfirmedUsers} = useGlobalContext()
    useUnconfirmedUsers()
    return (
        <section>
            {user &&
                <>
                    <Navbar/>
                    <SideBar/>
                    {isModalOpen && <AddModal/>}
                    <Hero/>
                    {unconfirmedUsers.length > 0 ? <AdminOverview/> : null}
                    <BoardOverview/>
                </>
            }
        </section>

    );
}

export default MainPage;