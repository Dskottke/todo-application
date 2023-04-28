import React from 'react';

function ToDo({title, description}) {
    return (
        <article className={"todo-container"}>
            <div className={"todo-header"}>
                <h1>{title}</h1>
            </div>
            <div className={"todo-info"}>
                <p>{description}</p>
            </div>
            <div className={"todo-action"}>
                <button className={"todo-btn"} type={"button"}>advance</button>
                <button className={"todo-btn"} type={"button"}>delete</button>
            </div>
        </article>
    );
}

export default ToDo;