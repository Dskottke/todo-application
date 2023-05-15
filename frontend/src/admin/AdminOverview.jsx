import React from 'react';
import UnconfirmedUserTable from "./UnconfirmedUserTable.jsx";

function AdminOverview() {
    return (

        <section className={"admin-overview-container"}>
            <UnconfirmedUserTable/>
        </section>
    );
}

export default AdminOverview;