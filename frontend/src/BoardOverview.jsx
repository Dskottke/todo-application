import React from 'react';
import Board from "./Board.jsx";
import {toDos} from "./data.js";

function BoardOverview() {
    return (
        <section className={"board-container"}>
            <Board title={"open"} toDos={toDos}/>
            <Board title={"in-progress"} toDos={toDos}/>
            <Board title={"done"} toDos={toDos}/>
        </section>
    );
}

export default BoardOverview;