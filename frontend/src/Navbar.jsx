import React from 'react';
import {useGlobalContext} from "./Context.jsx";
import {GrCheckmark, GrInProgress} from "react-icons/gr";
import {CgOpenCollective} from "react-icons/cg";

function Navbar(props) {
    const {openAmount, inProgressAmount, doneAmount} = useGlobalContext()

    return (
        <nav className={"nav-container"}>
            <div className={"nav-header"}>
                <h1><span>ToDo</span>-list</h1>
            </div>
            <div className={"nav-status-area"}>
                <p><CgOpenCollective/>open:<span className={"nav-status-counter"}>{openAmount}</span></p>
                <p><GrInProgress/>in-progress:<span className={"nav-status-counter"}>{inProgressAmount}</span></p>
                <p><GrCheckmark/>done: <span className={"nav-status-counter"}>{doneAmount}</span></p>
            </div>
        </nav>
    );
}

export default Navbar;