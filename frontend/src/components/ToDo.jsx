import React from 'react';
import {useAdvanceToDo, useDateFormatter, useDeleteToDo} from "../hooks.js";

function ToDo({id, title, description, status, creationDate, dueDate}) {

    const {mutate: advanceToDo} = useAdvanceToDo();
    const {mutate: deleteToDo} = useDeleteToDo();
    const {creationDateFormatted, dueDateFormatted, daysLeft} = useDateFormatter(creationDate,dueDate)

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
            <div className={"todo-dates"}>
                <p>{creationDateFormatted}</p>
                <p>{daysLeft}</p>
                <p>{dueDateFormatted}</p>
            </div>
            <div className={"todo-info"}>
                <p><span>{description}</span></p>
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