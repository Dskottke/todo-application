import React from 'react';
import Board from "./Board.jsx";
import {useQuery} from "react-query";
import customToDoFetch from "./customFetches.js";
import {useSortData} from "./hooks.js";

function BoardOverview() {

    const {isSuccess, isLoading, data, error} = useQuery({
        queryKey: ['todos'],
        queryFn: () => customToDoFetch.get("")
    })

    if (isLoading) {
        return (
            <div>
                <p>loading...</p>
            </div>
        )
    }
    console.log(error)
    if (error) {
        return (
            <div>
                <p>{error.message}</p>
            </div>
        )
    }
    if (isSuccess) {
        const {openList, inProgressList, closed} = useSortData(data.data)


        return (
            <section className={"board-container"}>
                <Board title={"open"} toDos={openList}/>
                <Board title={"in-progress"} toDos={inProgressList}/>
                <Board title={"done"} toDos={closed}/>
            </section>
        );
    }
}

export default BoardOverview;