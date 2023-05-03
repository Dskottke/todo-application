import React from 'react';
import {useAdvanceToDo, useDeleteToDo} from "../hooks.js";

function ToDo({id, title, description, status}) {

    const {mutate: advanceToDo} = useAdvanceToDo();
    const {mutate: deleteToDo} = useDeleteToDo();


    const onDeleteClick = () => {
        deleteToDo(id)
    }
    const onAdvanceClick = () => {
        advanceToDo(id)
    }

    return (
        <article className={"todo-container"}>
            <div className={"todo-header"}>
                <h1>{title}</h1>
            </div>
            <div className={"todo-info"}>
                <p>{description}</p>
            </div>
            <div className={"todo-action"}>
                {status !== "DONE" ?
                    <button onClick={onAdvanceClick} className={"todo-btn"} type={"button"}>advance</button> : null}
                <button onClick={onDeleteClick} className={"todo-btn"} type={"button"}>delete</button>
            </div>
        </article>
    );
}

export default ToDo;