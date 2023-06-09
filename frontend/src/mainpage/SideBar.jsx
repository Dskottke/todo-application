import React from 'react';
import {AiOutlineCloseCircle} from "react-icons/ai";
import {useGlobalContext} from "../Context.jsx";

function SideBar() {
    const {
        isSideBarOpen, closeSideBar, openAmount,
        inProgressAmount,
        doneAmount,
        getCurrentUser,
        logoutUser,
        navigateTo
    } = useGlobalContext()


    return (
        <aside className={isSideBarOpen ? "sidebar-container  show-sidebar" : "sidebar-container "}>
            <section className={"sidebar-header"}>
                <div className={"nav-side-header"}>
                    <h1><span>ToDo</span>-list</h1>
                </div>
            </section>
            <div className={"head_underline"}></div>
            <section className={"sidebar-todos"}>
                <p>open:<span className={"status-counter"}>{openAmount}</span></p>
                <p>in-progress:<span className={"status-counter"}>{inProgressAmount}</span></p>
                <p>done:<span className={"status-counter"}>{doneAmount}</span></p>
            </section>
            <div className={"head_underline"}></div>
            <section className={"sidebar-user"}>
                <p className={"sidebar-user"}>Logged in as: <span>{getCurrentUser.username}</span></p>
                <button onClick={logoutUser} className={"btn logout-btn"}>Logout</button>
                {getCurrentUser.role === "ADMIN" && window.location.pathname === "/" ?
                    <button onClick={() => navigateTo("/admin")} className={"btn logout-btn"}>Admin</button>
                    : null
                }
                {window.location.pathname === "/admin" ?
                    <button onClick={() => navigateTo("/")} className={"btn logout-btn"}>Main</button>
                    : null
                }
            </section>
            <button onClick={closeSideBar} className={"btn sidebar-btn"}><AiOutlineCloseCircle/></button>
            <div className={"head_underline"}></div>
        </aside>
    );
}

export default SideBar;