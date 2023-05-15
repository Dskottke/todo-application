import React from 'react';
import UnconfirmedUserRow from "./UnconfirmedUserRow.jsx";
import {useUnconfirmedUsersFetch} from "../hooks.js";

function UnconfirmedUserTable() {
    const {data, isLoading, isError, isSuccess} = useUnconfirmedUsersFetch()

    if (isLoading) return <h1>Loading...</h1>
    if (isError) return <h1>Error...</h1>
    if (isSuccess) {

        const users = data.data
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
                    {users.map((user) => {
                        return <UnconfirmedUserRow key={user.id} {...user}/>
                    })
                    }
                    </tbody>
                </table>
            </section>
        );
    }
}

export default UnconfirmedUserTable;