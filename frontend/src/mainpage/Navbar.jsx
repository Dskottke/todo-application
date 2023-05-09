import React from 'react';
import {useGlobalContext} from "../Context.jsx";
import {GrMenu} from "react-icons/gr";

function Navbar() {
    const {
        openAmount,
        inProgressAmount,
        doneAmount,
        getCurrentUser,
        logoutUser,
        openSideBar,
    } = useGlobalContext()


    return (
        <nav className={"nav-container"}>
            <div className={"nav-side-header"}>
                <h1><span>ToDo</span>-list</h1>
            </div>

            <div className={"nav-status-area"}>
                <div>
                    <p>open:<span className={"status-counter"}>{openAmount}</span></p>
                </div>
                <div>
                    <p>in-progress:<span className={"status-counter"}>{inProgressAmount}</span></p>
                </div>
                <div>
                    <p>done: <span className={"status-counter"}>{doneAmount}</span></p>
                </div>
            </div>
            <div className={"nav-user"}>
                <p className={"nav-user"}>Logged in as: <span>{getCurrentUser}</span></p>
                <button onClick={logoutUser} className={"btn nav-btn"}>Logout</button>
            </div>
            <div className={"nav-btn-area"}>
                <button onClick={openSideBar} className={"btn nav-menu-btn"}><GrMenu/></button>
            </div>
        </nav>
    );
}

export default Navbar;