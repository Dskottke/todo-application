import React from 'react';
import {useConfirmUser, useDeleteUser} from "../hooks.js";

function UnconfirmedUserRow({id, username, role}) {
    const {mutate: deleteUser} = useDeleteUser()
    const {mutate: confirmUser} = useConfirmUser()
    const onDeleteClick = () => {
        deleteUser(id)
    }
    const confirmUserClick = () => {
        confirmUser(id)
    }
    return (
        <tr className={"unconfirmed-user-card"}>
            <td>{username}</td>
            <td>{role}</td>
            <td className={"user-card-action"}>
                <button onClick={confirmUserClick} className={"todo-btn"}>confirm</button>
                <button onClick={onDeleteClick} className={"todo-btn"}>delete</button>
            </td>
        </tr>
    );
}

export default UnconfirmedUserRow;