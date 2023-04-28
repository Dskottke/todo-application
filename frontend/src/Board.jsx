import React from 'react';
import ToDo from "./ToDo.jsx";

function Board({title, toDos}) {
    return (
        <section className={"board"}>
            <div className={"board-header"}>
                <h1>{title}</h1>
            </div>
            <div className={"board-todos"}>
                {toDos && toDos.map(todo => <ToDo key={todo.id} {...todo}/>)}
            </div>
        </section>
    );
}

export default Board;