import React from 'react';

function UnconfirmedUserRow({username, role}) {
    return (
        <tr className={"unconfirmed-user-card"}>
            <td>{username}</td>
            <td>{role}</td>
            <td className={"user-card-action"}>
                <button className={"todo-btn"}>confirm</button>
                <button className={"todo-btn"}>delete</button>
            </td>
        </tr>
    );
}

export default UnconfirmedUserRow;