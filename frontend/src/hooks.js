export const useSortData = (toDos) => {

    const openList = toDos.filter(todo =>
        todo.status === "OPEN"
    )
    const inProgressList = toDos.filter(todo =>
        todo.status === "IN_PROGRESS"
    )
    const closed = toDos.filter(todo =>
        todo.status === "DONE"
    )

    return {openList,inProgressList,closed}
}