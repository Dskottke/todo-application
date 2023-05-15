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
        navigateTo
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
                <p className={"nav-user"}>Logged in as: <span>{getCurrentUser.username}</span></p>
                <button onClick={logoutUser} className={"btn nav-btn"}>Logout</button>
                {getCurrentUser.role === "ADMIN" && window.location.pathname === "/" ?
                    <button onClick={() => navigateTo("/admin")} className={"btn nav-btn"}>Admin</button>
                    : null
                }
                {window.location.pathname === "/admin" ?
                    <button onClick={() => navigateTo("/")} className={"btn nav-btn"}>Main</button>
                    : null
                }
            </div>
            <div className={"nav-btn-area"}>
                <button onClick={openSideBar} className={"btn nav-menu-btn"}><GrMenu/></button>
            </div>
        </nav>
    );
}

export default Navbar;