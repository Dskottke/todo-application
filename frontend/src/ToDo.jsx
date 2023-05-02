import React from 'react';
import customToDoFetch from "./customFetches.js";
import {useMutation, useQueryClient} from "react-query";

function ToDo({id, title, description, status}) {
    const queryClient = useQueryClient()

    const {mutate: advanceToDo} = useMutation({
        mutationFn: (id) => customToDoFetch.put(`/${id}`),
        onSuccess: () => {
            queryClient.invalidateQueries({queryKey: ['todos']})
        }
    })
    console.log(status)
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
                {status !== "DONE" ?<button onClick={onAdvanceClick} className={"todo-btn"} type={"button"}>advance</button> : null}
                <button className={"todo-btn"} type={"button"}>delete</button>
            </div>
        </article>
    );
}

export default ToDo;