import React from 'react';
import UnconfirmedUserRow from "./UnconfirmedUserRow.jsx";
import {useGlobalContext} from "../Context.jsx";

function UnconfirmedUserTable(props) {
    const {unconfirmedUsers} = useGlobalContext()
    return (
        <section className={"unconfirmed-table"}>
            <h1>Unconfirmed users</h1>
            <div className={"head_underline"}/>
            <table>
                <thead>
                <tr>
                    <th>username</th>
                    <th>role</th>
                    <th>confirm</th>
                </tr>
                </thead>
                <tbody>
                {unconfirmedUsers.map((user) => {
                    return <UnconfirmedUserRow key={user.id} {...user}/>
                })
                }
                </tbody>
            </table>
        </section>
    );
}

export default UnconfirmedUserTable;