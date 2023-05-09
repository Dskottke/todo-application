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
    } = useGlobalContext()
    return (
        <aside className={isSideBarOpen ? "sidebar-container  show-sidebar" : "sidebar-container "}>
            <section className={"sidebar-header"}>
                <div className={"nav-side-header"}>
                    <h1><span>ToDo</span>-list</h1>
                </div>
            </section>
            <section className={"sidebar-todos"}>
                <p>open:<span className={"status-counter"}>{openAmount}</span></p>
                <p>in-progress:<span className={"status-counter"}>{inProgressAmount}</span></p>
                <p>done: <span className={"status-counter"}>{doneAmount}</span></p>
            </section>
            <div className={"head_underline"}></div>
            <section className={"sidebar-user"}>
                <p className={"sidebar-user"}>Logged in as: <span>{getCurrentUser}</span></p>
                <button onClick={logoutUser} className={"btn logout-btn"}>Logout</button>
            </section>
            <button onClick={closeSideBar} className={"btn sidebar-btn"}><AiOutlineCloseCircle/></button>
            <div className={"head_underline"}></div>
        </aside>
    );
}

export default SideBar;