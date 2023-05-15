import React from 'react';
import UnconfirmedUserTable from "./UnconfirmedUserTable.jsx";
import {useGlobalContext} from "../Context.jsx";
import SideBar from "../mainpage/SideBar.jsx";

function AdminOverview() {
   const {getCurrentUser} = useGlobalContext()

    if (getCurrentUser.role !== "ADMIN") {
        return <h1>Not Authorized</h1>
    }

    return (
        <section className={"admin-overview"}>
            <UnconfirmedUserTable/>
            <SideBar/>
        </section>
    );
}

export default AdminOverview;